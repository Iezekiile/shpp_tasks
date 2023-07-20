package com.example.shpp_task1

import android.app.Application
import com.example.shpp_task1.model.ContactsData
import com.example.shpp_task1.viewmodel.ContactsViewModel

class App : Application() {

    private val contactsData: ContactsData by lazy { ContactsData() }
    val contactViewModel: ContactsViewModel by lazy { ContactsViewModel(contactsData) }
}