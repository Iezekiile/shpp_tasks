package com.example.shpp_task1.presentation.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.ContactListItem
import com.example.shpp_task1.databinding.FragmentMyContactsBinding
import com.example.shpp_task1.presentation.fragments.addContact.AddContactDialogFragment
import com.example.shpp_task1.presentation.fragments.contacts.adapter.ContactsAdapter
import com.example.shpp_task1.presentation.fragments.contacts.interfaces.ContactsActionListener
import com.example.shpp_task1.presentation.fragments.contacts.utils.ContactsSpaceItemDecoration
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerFragment
import com.example.shpp_task1.presentation.utils.ext.setVisibility
import com.example.shpp_task1.presentation.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity class for displaying a list of contacts and managing contact-related interactions.
 */
@AndroidEntryPoint
class MyContactsFragment : Fragment(R.layout.fragment_my_contacts) {

    private val binding by viewBinding<FragmentMyContactsBinding>()
    private val viewModel by viewModels<MyContactsViewModel>()

    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(
            object : ContactsActionListener {
                override fun onContactClick(contactItem: ContactListItem) {
                    viewModel.onContactClick(contactItem)
                }

                override fun onContactDelete(contactItem: ContactListItem) {
                   viewModel.onContactDelete(contactItem)
                }

                override fun onLongClick(contactItem: ContactListItem) {
                    viewModel.onLongClick(contactItem)
                }

            }
        )
    }

    //todo atach in fragment
//    val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
//    itemTouchHelper.attachToRecyclerView(recyclerView)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setContactsObserver()
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

    private fun setListeners() {
        binding.addContacts.setOnClickListener { createDialogFragment() }
        binding.deleteAll.setOnClickListener { viewModel.deleteSelectedContacts() }
        binding.buttonBack.setOnClickListener { navigateBack() }
    }

    private fun setContactsObserver() {
        viewModel.contacts.observe(viewLifecycleOwner) { state ->
            adapter.updateList(state.contactList)
            binding.deleteAll.setVisibility(state.multiselectMode)
        }
        viewModel.multiselectMode.observe(viewLifecycleOwner) { multiselectMode ->
            binding.deleteAll.setVisibility(multiselectMode)
            adapter.isMultiselect = multiselectMode
        }
    }

    private fun createDialogFragment() {
        val dialog = AddContactDialogFragment(viewModel)
        dialog.show(parentFragmentManager, "AddContactDialogFragment")
    }

    private fun navigateBack() {
            (parentFragment as ViewPagerFragment).viewPager.currentItem = 0 //todo ENUM
    }
}