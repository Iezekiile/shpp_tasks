package com.example.shpp_task1.presentation.fragments.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.databinding.ItemContactBinding
import com.example.shpp_task1.presentation.fragments.contacts.MyContactsFragmentDirections
import com.example.shpp_task1.presentation.fragments.contacts.utils.ContactsDiffCallback
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsActionListener
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.example.shpp_task1.utils.ext.setImageByGlide
import com.example.shpp_task1.utils.ext.setImageByPicasso
import com.google.android.material.snackbar.Snackbar


/**
 * The duration in milliseconds for which the Snackbar is displayed.
 */
const val SNACKBAR_DURATION = 5000

/**
 * RecyclerView adapter class for displaying contacts.
 *
 * @param actionListener The listener for the adapter actions.
 * @param recycler The RecyclerView instance.
 * @param navController The NavController instance.
 */
class ContactsAdapter(
    private val actionListener: ContactsActionListener,
    recycler: RecyclerView,
    private val navController: NavController
) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), View.OnClickListener {
    private val recyclerView = recycler

    /**
     * Initializes the swipe-to-delete functionality for the RecyclerView.
     */
    init {
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

     /**
     * The list of contacts to display.
     */
    var contacts: List<Contact> = emptyList()
        set(newValue) {
            val diffCallback = ContactsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    /**
     * Click event handler for different views in the item layout.
     *
     * @param v The clicked view.
     */
    override fun onClick(v: View) {
        val contact = v.tag as Contact
        when (v.id) {
            R.id.deleteImageViewButton -> {
                actionListener.onContactDelete(contact)
                showUndoSnackbar(contact)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    /**
     * Creates a new ViewHolder instance when needed.
     *
     * @param parent The parent view group.
     * @param viewType The type of the view.
     * @return The created ViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        binding.deleteImageViewButton.setOnClickListener(this)
        return ContactsViewHolder(binding)
    }

    /**
     * ViewHolder class for representing each item in the list.
     *
     * @param binding The ViewBinding for the item layout.
     */
    inner class ContactsViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setBackgroundResource(R.drawable.bg_view_holder)
        }
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            holder.itemView.tag = contact
            deleteImageViewButton.tag = contact
            contactNameTextView.text = contact.username
            contactCarrierTextView.text = contact.career
            if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(contact.avatar)
            else avatar.setImageByPicasso(contact.avatar)
        }
        setContactDetailListener(holder, contact)
    }

    /**
     * Sets the listener for the contact detail view.
     *
     * @param holder The ViewHolder to bind data to.
     * @param contact The contact to bind data to.
     */
    private fun setContactDetailListener(holder: ContactsViewHolder, contact: Contact) {
        holder.itemView.setOnClickListener {
            val extras: Array<Pair<View, String>>
            with(holder.binding) {
                extras = arrayOf(
                    setTransitionName(
                        avatar,
                        Constants.TRANSITION_NAME_AVATAR + contact.id
                    ),
                    setTransitionName(
                        contactNameTextView,
                        Constants.TRANSITION_NAME_USERNAME + contact.id
                    ), setTransitionName(
                        contactCarrierTextView,
                        Constants.TRANSITION_NAME_CAREER + contact.id
                    )
                )
            }
            val action =
                MyContactsFragmentDirections.actionMyContactsFragmentToDetailViewFragment(contact)
            navController.navigate(action, FragmentNavigatorExtras(*extras))
        }
    }

    /**
     * Sets the transition name for a view.
     *
     * @param view The view to set the transition name for.
     * @param name The transition name.
     * @return The view and transition name pair.
     */
    private fun setTransitionName(view: View, name: String): Pair<View, String> {
        view.transitionName = name
        return view to name
    }

    /**
     * Displays a Snackbar with the option to undo the contact deletion.
     *
     * @param contact The deleted contact.
     */
    private fun showUndoSnackbar(contact: Contact) {
        val snackbar = Snackbar.make(
            recyclerView,
            "Contact has been deleted ${contact.username}?",
            Snackbar.LENGTH_LONG
        )

        snackbar.duration = SNACKBAR_DURATION
        snackbar.setAction("UNDO") {
            snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
            snackbar.dismiss()

            actionListener.onContactRestore(contact)
        }
        snackbar.show()
    }

    /**
     * Callback class for handling swipe-to-delete functionality.
     */
    private inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT
    ) {

        /**
         * Called when the item is dragged or swiped.
         */
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        /**
         * Called when the item is swiped.
         */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            val contact = contacts[position]
            actionListener.onContactSwipeToDelete(position)
            showUndoSnackbar(contact)
        }
    }
}
