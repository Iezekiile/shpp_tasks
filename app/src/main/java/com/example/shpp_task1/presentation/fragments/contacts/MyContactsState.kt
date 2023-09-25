package com.example.shpp_task1.presentation.fragments.contacts

import com.example.shpp_task1.data.model.ContactListItem

data class MyContactsState(
    val contactList: List<ContactListItem>,
    val multiselectMode: Boolean
)