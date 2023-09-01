package com.example.shpp_task1.presentation.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.databinding.FragmentMyContactsBinding
import com.example.shpp_task1.presentation.fragments.addContact.AddContactDialogFragment
import com.example.shpp_task1.presentation.fragments.contacts.adapter.ContactsAdapter
import com.example.shpp_task1.presentation.fragments.contacts.utils.ContactsSpaceItemDecoration
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsActionListener
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsViewModel
import com.example.shpp_task1.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity class for displaying a list of contacts and managing contact-related interactions.
 */
@AndroidEntryPoint
class MyContactsFragment : Fragment(R.layout.fragment_my_contacts) {

    private val binding by viewBinding<FragmentMyContactsBinding>()
    private val contactsViewModel by viewModels<ContactsViewModel>()
    private lateinit var adapter: ContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
        setObservers()
        setRecyclerSettings()
    }

    /**
     * Sets the RecyclerView's layout manager and adds item spacing.
     */
    private fun setRecyclerSettings() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(context)
        val spaceBetweenItemsInPixels = resources.getDimensionPixelSize(R.dimen.margin_all_l)
        binding.recyclerContacts.addItemDecoration(
            ContactsSpaceItemDecoration(
                spaceBetweenItemsInPixels
            )
        )
        binding.recyclerContacts.adapter = adapter
    }

    /**
     * Initializes the adapter with a ContactActionListener to handle contact-related actions.
     */
    private fun setAdapter() {

        adapter = ContactsAdapter(object : ContactsActionListener {
            override fun onContactDelete(contact: Contact) {
                contactsViewModel.onContactDelete(contact)
            }

            override fun onContactRestore(contact: Contact) {
                contactsViewModel.onContactRestore(contact)
            }

            override fun onContactSwipeToDelete(index: Int) {
                contactsViewModel.onContactSwipeToDelete(index)
            }

            override fun onContactAdd(contact: Contact) {
                contactsViewModel.onContactAdd(contact)
            }
        }, binding.recyclerContacts, findNavController())
    }

    /**
     * Sets listeners for UI elements.
     */
    private fun setListeners() {
        setAddContactListener()
    }


    /**
     * Sets observers to update the adapter when contact data changes.
     */
    private fun setObservers() {
        setContactsObserver()
    }

    /**
     * Observes changes in the contact data and updates the adapter accordingly.
     */
    private fun setContactsObserver() {
        contactsViewModel.contacts.observe(viewLifecycleOwner) {
            adapter.contacts = it
        }
    }

    /**
     * Sets a click listener for the "Add Contacts" button to open the add contact dialog.
     */

    private fun setAddContactListener() {
        binding.addContacts.setOnClickListener {
            val dialog = AddContactDialogFragment(contactsViewModel)
            dialog.show(parentFragmentManager, "AddContactDialogFragment")
        }
    }
}