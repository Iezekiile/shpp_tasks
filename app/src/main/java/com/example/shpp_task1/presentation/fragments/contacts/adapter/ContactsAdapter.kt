package com.example.shpp_task1.presentation.fragments.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.ContactListItem
import com.example.shpp_task1.databinding.ItemContactBinding
import com.example.shpp_task1.presentation.fragments.contacts.utils.ContactsDiffCallback
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsViewModel
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerFragmentDirections
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
 * Adapter class for displaying a list of contacts.
 *
 * @param contactsViewModel The ViewModel for managing contacts.
 * @param recycler The RecyclerView for displaying the list of contacts.
 * @param navController The NavController for navigating between fragments.
 * @param viewLifecycleOwner The LifecycleOwner for the current view.
 */
class ContactsAdapter(
    private val contactsViewModel: ContactsViewModel,
    recycler: RecyclerView,
    private val navController: NavController,
    private val viewLifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {

    private val recyclerView = recycler
    var contacts: List<ContactListItem> = emptyList()
    private var multiselectMode: Boolean = false

    init {
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
        setObservers()
    }

    override fun onClick(v: View) {
        val contact = v.tag as ContactListItem
        when (v.id) {
            R.id.deleteImageViewButton -> {
                contactsViewModel.onContactDelete(contact)
                showUndoSnackbar(contact)
                println("delete button" + contact.username)
            }

            R.id.checkbox -> {
                contactsViewModel.onContactToggle(contact)
                println("checkbox" + contact.username)
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        val contact = v.tag as ContactListItem
        contactsViewModel.onContactToggle(contact)
        return true
    }

    fun updateList(newList: List<ContactListItem>) {
        val diffCallback = ContactsDiffCallback(contacts, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        contacts = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        binding.deleteImageViewButton.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
        return ContactsViewHolder(binding)
    }

    inner class ContactsViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            if (multiselectMode) binding.checkbox.visibility = View.VISIBLE
            else binding.checkbox.visibility = View.GONE
            itemView.setBackgroundResource(R.drawable.bg_view_holder)
        }
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            holder.itemView.tag = contact
            deleteImageViewButton.tag = contact
            contactNameTextView.text = contact.username
            contactCarrierTextView.text = contact.career
            checkbox.setOnClickListener { contactsViewModel.onContactToggle(contact) }
            if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(contact.avatar)
            else avatar.setImageByPicasso(contact.avatar)

            if (multiselectMode) {
                if (contact.isChecked) {
                    root.setBackgroundResource(R.drawable.bg_view_holder_selected)
                } else {
                    root.setBackgroundResource(R.drawable.bg_view_holder)
                }
                checkbox.visibility = View.VISIBLE
                checkbox.isChecked = contact.isChecked
            } else {
                checkbox.visibility = View.GONE
                root.setBackgroundResource(R.drawable.bg_view_holder)
            }
        }
        setContactDetailListener(holder, contact)
    }

    private fun setContactDetailListener(holder: ContactsViewHolder, contact: ContactListItem) {
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
                ViewPagerFragmentDirections.actionViewPagerToDetailViewFragment(contact.originContact)
            navController.navigate(action, FragmentNavigatorExtras(*extras))
        }
    }

    private fun setTransitionName(view: View, name: String): Pair<View, String> {
        view.transitionName = name
        return view to name
    }

    private fun setObservers() {
        setMultiselectModeObserver()
    }

    private fun setMultiselectModeObserver() {
        contactsViewModel.multiselectModeLiveData.observe(viewLifecycleOwner) { state ->
            if (state >= 1) {
                if (!multiselectMode) {
                    multiselectMode = true
                    notifyDataSetChanged()
                }
            } else {
                multiselectMode = false
                notifyDataSetChanged()
            }
        }
    }

    private fun showUndoSnackbar(contact: ContactListItem) {
        val snackbar = Snackbar.make(
            recyclerView,
            "Contact has been deleted ${contact.username}?",
            Snackbar.LENGTH_LONG
        )

        snackbar.duration = SNACKBAR_DURATION
        snackbar.setAction("UNDO") {
            snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
            snackbar.dismiss()
            contactsViewModel.onContactRestore(contact)
        }
        snackbar.show()
    }

    private inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            val contact = contacts[position]
            contactsViewModel.onContactDelete(contact)
            showUndoSnackbar(contact)
        }
    }
}
