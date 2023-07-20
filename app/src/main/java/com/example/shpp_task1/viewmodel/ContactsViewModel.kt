package com.example.shpp_task1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shpp_task1.model.Contact
import com.example.shpp_task1.model.ContactsData

interface RecyclerListener {

    fun onItemClicked()
    fun onButtonClicked(contact: Contact)
}

class ContactsViewModel(private val contactsData: ContactsData) : ViewModel(), RecyclerListener{

    constructor() : this(ContactsData())

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        val contactsDataList = contactsData.getContacts()
        _contacts.value = contactsDataList
    }

    private fun deleteContact(contact: Contact){
        contactsData.deleteContact(contact)
        loadContacts()
    }

    override fun onButtonClicked(contact: Contact) {
        deleteContact(contact)
    }

    override fun onItemClicked() {
        TODO("Not yet implemented")
    }
}