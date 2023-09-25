package com.example.shpp_task1.presentation.fragments.contacts.adapter

import android.annotation.SuppressLint
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
import com.example.shpp_task1.presentation.fragments.contacts.MyContactsViewModel
import com.example.shpp_task1.presentation.fragments.contacts.interfaces.ContactsActionListener
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerFragmentDirections
import com.example.shpp_task1.presentation.utils.ext.setImageByGlide
import com.example.shpp_task1.presentation.utils.ext.setImageByPicasso
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.google.android.material.snackbar.Snackbar


/**
 * The duration in milliseconds for which the Snackbar is displayed.
 */
private const val SNACKBAR_DURATION = 5000

/**
 * Adapter class for displaying a list of contacts.
 *
 * @param listener The ViewModel for managing contacts.
 * @param recycler The RecyclerView for displaying the list of contacts.
 * @param navController The NavController for navigating between fragments.
 * @param viewLifecycleOwner The LifecycleOwner for the current view.
 */
class ContactsAdapter(
    private val listener: ContactsActionListener,
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {    //todo ListAdapter + DiffUtil

    var isMultiselect: Boolean = false

    var contacts: List<ContactListItem> = emptyList()
    private var multiselectMode: Boolean = false

//    init {
//        setObservers()
//    }

//    override fun onClick(v: View) {
//        val contact = v.tag as ContactListItem
//        when (v.id) {
//            R.id.deleteImageViewButton -> {
//                listener.onContactDelete(contact)
//                showUndoSnackbar(contact)
//                println("delete button" + contact.username)
//            }
//
//            R.id.checkbox -> {
//                listener.onContactToggle(contact)
//                println("checkbox" + contact.username)
//            }
//        }
//    }

//    override fun onLongClick(v: View): Boolean {
//        val contact = v.tag as ContactListItem
//        listener.onContactToggle(contact)
//        return true
//    }

    fun updateList(newList: List<ContactListItem>) {
        val diffCallback = ContactsDiffCallback(
            contacts,
            newList
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        contacts = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(
            inflater,
            parent,
            false
        )
        //binding.root.setOnLongClickListener(this)
        return ContactsViewHolder(binding)
    }

    inner class ContactsViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            if (multiselectMode) binding.checkbox.visibility = View.VISIBLE
            else binding.checkbox.visibility = View.GONE
            itemView.setBackgroundResource(R.drawable.bg_view_holder)
        }

        fun bind(contact: ContactListItem) {
            with(binding) {
                itemView.tag = contact
                deleteImageViewButton.setOnClickListener { listener.onContactDelete(contact) }
                contactNameTextView.text = contact.username
                contactCarrierTextView.text = contact.career
                checkbox.setOnClickListener { listener.onContactClick(contact) }
                if (com.example.shpp_task1.utils.constants.FeatureFlags.USE_GLIDE) avatar.setImageByGlide(contact.avatar)
                else avatar.setImageByPicasso(contact.avatar)

                if (multiselectMode) {      //todo  override fun getItemViewType(position: Int): Int {
                    if (contact.isChecked) {
                        root.setBackgroundResource(com.example.shpp_task1.R.drawable.bg_view_holder_selected)
                    } else {
                        root.setBackgroundResource(com.example.shpp_task1.R.drawable.bg_view_holder)
                    }
                    checkbox.visibility = android.view.View.VISIBLE
                    checkbox.isChecked = contact.isChecked
                } else {
                    checkbox.visibility = android.view.View.GONE
                    root.setBackgroundResource(com.example.shpp_task1.R.drawable.bg_view_holder)
                }
                root.setOnClickListener { listener.onContactClick(contact) }
            }
        }

    }

    override fun onBindViewHolder(
        holder: ContactsViewHolder,
        position: Int
    ) {
        holder.bind(contacts[position])
    }

    private fun setContactDetailListener(
        holder: ContactsViewHolder,
        contact: ContactListItem
    ) {
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
                    ),
                    setTransitionName(
                        contactCarrierTextView,
                        Constants.TRANSITION_NAME_CAREER + contact.id
                    )
                )
            }
            val action =
                ViewPagerFragmentDirections.actionViewPagerToDetailViewFragment(contact.originContact)
//            navController.navigate(
//                action,
//                FragmentNavigatorExtras(*extras)
//            )
        }
    }

    private fun setTransitionName(
        view: View,
        name: String
    ): Pair<View, String> {
        view.transitionName = name
        return view to name
    }

//    private fun setObservers() {
//        setMultiselectModeObserver()
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun setMultiselectModeObserver() {
//            if (isMultiselect) {
//                if (!multiselectMode) {
//                    multiselectMode = true
//                    notifyDataSetChanged()  //todo baD!!!
//                }
//            } else {
//                multiselectMode = false
//                notifyDataSetChanged()
//            }
//        }
//    }

//    private fun showUndoSnackbar(contact: ContactListItem) {
//        val snackbar = Snackbar.make(
//            recyclerView,
//            "Contact has been deleted ${contact.username}?",
//            Snackbar.LENGTH_LONG
//        )
//
//        snackbar.duration = SNACKBAR_DURATION
//        snackbar.setAction("UNDO") {
//            snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
//            snackbar.dismiss()
//            listener.onContactRestore(contact)
//        }
//        snackbar.show()
//    }

//    private inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
//        0,
//        ItemTouchHelper.RIGHT
//    ) {
//        override fun isItemViewSwipeEnabled(): Boolean {
//            return !multiselectMode
//        }
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            return false
//        }
//
//        override fun onSwiped(
//            viewHolder: RecyclerView.ViewHolder,
//            direction: Int
//        ) {
//            val position = viewHolder.bindingAdapterPosition
//            val contact = contacts[position]
//            listener.onContactDelete(contact)
//            showUndoSnackbar(contact)
//        }
//    }
}
