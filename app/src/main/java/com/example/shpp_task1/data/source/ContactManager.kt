package com.example.shpp_task1.data.source

import com.example.shpp_task1.data.model.Contact

interface ContactManager {
    fun addContact(contact: Contact)
    fun deleteContact(contact: Contact)
    fun deleteContact(index: Int)
    fun getContactOnIndex(index: Int): Contact
    fun getContacts(): MutableList<Contact>
}
