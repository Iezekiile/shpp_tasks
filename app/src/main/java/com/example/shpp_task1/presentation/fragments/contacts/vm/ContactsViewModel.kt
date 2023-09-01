package com.example.shpp_task1.presentation.fragments.contacts.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.data.source.RepositoryContacts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel class for displaying a list of contacts and managing contact-related interactions.
 *
 * @param repositoryContacts The repository for managing contacts.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(private val repositoryContacts: RepositoryContacts) : ViewModel(),
    ContactsActionListener {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        val contactsDataList = repositoryContacts.getContacts()
        _contacts.value = contactsDataList
    }

    override fun onContactDelete(contact: Contact) {
        repositoryContacts.deleteContact(contact)
        loadContacts()
    }

    override fun onContactRestore(contact: Contact) {
        repositoryContacts.addContact(contact)
        loadContacts()
    }

    override fun onContactSwipeToDelete(index: Int) {
        repositoryContacts.deleteContact(index)
        loadContacts()
    }


    override fun onContactAdd(contact: Contact) {
        repositoryContacts.addContact(contact)
        loadContacts()
    }
}