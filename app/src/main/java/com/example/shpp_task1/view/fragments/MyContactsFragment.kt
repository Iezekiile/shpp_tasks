package com.example.shpp_task1.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentAddContactBinding
import com.example.shpp_task1.databinding.FragmentMyContactsBinding
import com.example.shpp_task1.databinding.ItemContactBinding
import com.example.shpp_task1.model.Contact
import com.example.shpp_task1.view.adapters.ContactActionListener
import com.example.shpp_task1.view.adapters.ContactsAdapter
import com.example.shpp_task1.view.adapters.SpaceItemDecoration
import com.example.shpp_task1.viewmodel.ContactsViewModel

/**
 * Activity class for displaying a list of contacts and managing contact-related interactions.
 */
class MyContactsFragment : Fragment() {

    private lateinit var binding: FragmentMyContactsBinding
    private lateinit var itemContactBinding: ItemContactBinding
    private lateinit var addContactBinding: FragmentAddContactBinding
    private lateinit var adapter: ContactsAdapter
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var navController: NavController

    /**
     * Called when the activity is starting. Responsible for initializing various components.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyContactsBinding.inflate(layoutInflater)
        addContactBinding = FragmentAddContactBinding.inflate(layoutInflater)
        itemContactBinding = ItemContactBinding.inflate(layoutInflater)
        navController = findNavController()
        setAdapter()
        setViewModel()
        setListeners()
        setObservers()
        setRecyclerSettings()
        return binding.root
    }

    /**
     * Sets the RecyclerView's layout manager and adds item spacing.
     */
    private fun setRecyclerSettings() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(context)
        val spaceBetweenItemsInPixels = resources.getDimensionPixelSize(R.dimen.margin_all_l)
        binding.recyclerContacts.addItemDecoration(SpaceItemDecoration(spaceBetweenItemsInPixels))
        binding.recyclerContacts.adapter = adapter
    }

    /**
     * Initializes the ViewModel and connects it to the adapter.
     */
    private fun setViewModel() {
        contactsViewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        contactsViewModel.addAdapter(adapter)
    }

    /**
     * Initializes the adapter with a ContactActionListener to handle contact-related actions.
     */
    private fun setAdapter() {

        adapter = ContactsAdapter(object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                contactsViewModel.onContactDelete(contact)
            }

            override fun onContactDetails(contact: Contact) {
                contactsViewModel.onContactDetails(contact)
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
        }, binding.recyclerContacts,  findNavController())
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