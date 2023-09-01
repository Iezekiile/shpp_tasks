package com.example.shpp_task1.presentation.fragments.contacts.vm

import com.example.shpp_task1.data.model.Contact

/**
 * Interface for handling contact-related actions in the UI.
 */
interface ContactsActionListener {
    fun onContactDelete(contact: Contact)
    fun onContactRestore(contact: Contact)
    fun onContactSwipeToDelete(index: Int)
    fun onContactAdd(contact: Contact)
}