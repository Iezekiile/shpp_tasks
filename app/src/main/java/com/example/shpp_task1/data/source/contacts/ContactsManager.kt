package com.example.shpp_task1.data.source.contacts

import com.example.shpp_task1.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Interface for contacts manager
 */
interface ContactsManager {
    fun addContact(contact: Contact)
    fun deleteContact(id: String?)
    fun getContacts(): Flow<List<Contact>>
}
