package com.example.shpp_task1.presentation.fragments.contacts.interfaces

import com.example.shpp_task1.data.model.ContactListItem

/**
 * Interface for handling contact-related actions in the UI.
 */
interface ContactsActionListener {
    fun onContactClick(contactItem: ContactListItem)
    fun onContactDelete(contactItem: ContactListItem)
    fun onLongClick(contactItem: ContactListItem)

}