package com.example.shpp_task1.presentation.fragments.contacts.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.shpp_task1.data.model.ContactListItem

/**
 * Custom DiffUtil class for calculating the difference between two lists of contacts.
 *
 * @param oldList The old list of contacts.
 * @param newList The new list of contacts.
 */
class ContactsDiffCallback(
    private val oldList: List<ContactListItem>,
    private val newList: List<ContactListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact.id == newContact.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact == newContact
    }
}