package com.example.shpp_task1.data.source.contacts

import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.data.model.ContactsGenerator
import com.example.shpp_task1.multiselect.MultiselectState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


const val CONTACTS_COUNT = 20

/**
 * Repository for contacts
 */
@Singleton
class RepositoryContacts @Inject constructor() : ContactsManager {


    private val contactsFlow =
        MutableStateFlow(ContactsGenerator.generateContactsList(CONTACTS_COUNT))


    override fun addContact(contact: Contact) {
        contactsFlow.update { oldList ->
            oldList.toMutableList().apply {
                add(0, contact)
            }
        }
    }

    fun deleteSelectedCats(multiChoiceState: MultiselectState<Contact>) {
        contactsFlow.update { oldList ->
            oldList.filter { contact -> !multiChoiceState.isChecked(contact) }
        }
    }

    override fun deleteContact(id: String?) {
        contactsFlow.update { oldList ->
            oldList.filter { it.id != id }
        }
    }

    override fun getContacts(): Flow<List<Contact>> {
        return contactsFlow
    }
}