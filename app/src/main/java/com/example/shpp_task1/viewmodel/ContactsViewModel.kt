package com.example.shpp_task1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shpp_task1.model.Contact
import com.example.shpp_task1.model.ContactsData
import com.example.shpp_task1.view.adapters.ContactActionListener
import com.example.shpp_task1.view.adapters.ContactsAdapter


class ContactsViewModel(private val contactsData: ContactsData) : ViewModel(),
    ContactActionListener {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> get() = _contacts

    private var adapter: ContactsAdapter? = null

    fun addAdapter(adapter: ContactsAdapter){
        this.adapter = adapter
    }
    constructor() : this(ContactsData())

    init {
        loadContacts()
    }

    private fun loadContacts() {
        val contactsDataList = contactsData.getContacts()
        _contacts.value = contactsDataList 
    }

    override fun onContactDelete(contact: Contact) {
        adapter?.notifyItemRemoved(contactsData.deleteContact(contact))
    }

    override fun onContactDetails(contact: Contact) {

    }

    override fun onContactRestore(contact: Contact) {
        contactsData.addContact(contact)
        adapter?.notifyItemInserted(0)
    }

    override fun onContactSwipeToDelete(index: Int) {
        contactsData.deleteContact(contactsData.getContactOnIndex(index))
        adapter?.notifyItemRemoved(index)
    }

    override fun onContactAdd(contact: Contact) {
        contactsData.addContact(contact)
        adapter?.notifyItemInserted(0)
    }

}