package com.example.shpp_task1.presentation.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentMyContactsBinding
import com.example.shpp_task1.presentation.fragments.addContact.AddContactDialogFragment
import com.example.shpp_task1.presentation.fragments.contacts.adapter.ContactsAdapter
import com.example.shpp_task1.presentation.fragments.contacts.utils.ContactsSpaceItemDecoration
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsViewModel
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerProvider
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
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = parentFragment as ViewPagerProvider?
        viewPager = provider!!.getViewPager()
        setAdapter()
        setListeners()
        setObservers()
        setRecyclerSettings()
    }

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

    private fun setAdapter() {
        adapter = ContactsAdapter(
            contactsViewModel,
            binding.recyclerContacts,
            findNavController(),
            viewLifecycleOwner
        )
    }

    private fun setListeners() {
        setAddContactListener()
        setDeleteAllListener()
        setBackListener()
    }

    private fun setObservers() {
        setContactsObserver()
    }


    private fun setContactsObserver() {
        contactsViewModel.contacts.observe(viewLifecycleOwner) { state ->
            adapter.updateList(state.contactList)
            setMultiselectMode(state.multiselectMode)
        }
    }

    private fun setMultiselectMode(multiselectMode: Boolean) {
        if (multiselectMode) {
            binding.deleteAll.visibility = View.VISIBLE
        } else {
            binding.deleteAll.visibility = View.GONE
        }
    }

    private fun setAddContactListener() {
        binding.addContacts.setOnClickListener {
            val dialog = AddContactDialogFragment(contactsViewModel)
            dialog.show(parentFragmentManager, "AddContactDialogFragment")
        }
    }

    private fun setDeleteAllListener() {
        binding.deleteAll.setOnClickListener {
            contactsViewModel.deleteSelectedContacts()
        }
    }

    private fun setBackListener() {
        binding.buttonBack.setOnClickListener {
            viewPager.currentItem = 0
        }
    }
}