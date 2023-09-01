package com.example.shpp_task1.data.source

import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.data.model.ContactsGenerator
import javax.inject.Inject
import javax.inject.Singleton


typealias ContactsListener = (users: List<Contact>) -> Unit
const val CONTACTS_COUNT = 20
@Singleton
class RepositoryContacts @Inject constructor() : ContactManager {

    private var contacts = ContactsGenerator.generateContactsList(CONTACTS_COUNT)
    private val listeners = mutableSetOf<ContactsListener>()

    override fun addContact(contact: Contact) {
        contacts = ArrayList(contacts)
        if (contacts.indexOf(contact) != -1) return
        contacts.add(0, contact)
        notifyChanges()
    }

    override fun deleteContact(contact: Contact) {
        val indexToDelete = contacts.indexOfFirst { it.id == contact.id }
        if (indexToDelete != -1) {
            contacts = ArrayList(contacts)
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    override fun deleteContact(index: Int) {
        if (index in 0 until contacts.size) {
            contacts = ArrayList(contacts)
            contacts.removeAt(index)
            notifyChanges()
        }
    }

    override fun getContactOnIndex(index: Int): Contact {
        return contacts[index]
    }

    override fun getContacts(): MutableList<Contact> {
        return contacts
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }
}