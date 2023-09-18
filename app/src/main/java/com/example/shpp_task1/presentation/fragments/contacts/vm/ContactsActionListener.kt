package com.example.shpp_task1.presentation.fragments.contacts.vm

import com.example.shpp_task1.data.model.ContactListItem

/**
 * Interface for handling contact-related actions in the UI.
 */
interface ContactsActionListener {
    fun onContactDelete(contact: ContactListItem)
    fun onContactRestore(contact: ContactListItem)
    fun onContactAdd(contact: ContactListItem)
    fun onContactToggle(contact: ContactListItem)
    fun onMultiSelectMode(multiselectMode: Int)
    fun deleteSelectedContacts()
}