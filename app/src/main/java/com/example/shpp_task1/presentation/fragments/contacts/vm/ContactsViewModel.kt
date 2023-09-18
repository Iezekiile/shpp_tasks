package com.example.shpp_task1.presentation.fragments.contacts.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.data.model.ContactListItem
import com.example.shpp_task1.data.source.contacts.RepositoryContacts
import com.example.shpp_task1.di.ContactsMultiselect
import com.example.shpp_task1.multiselect.MultiselectHandler
import com.example.shpp_task1.multiselect.MultiselectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing contact data.
 *
 * @param repositoryContacts The repository responsible for managing contact data.
 * @param multiselectHandler The handler responsible for managing multiselect mode.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repositoryContacts: RepositoryContacts,
    @ContactsMultiselect private val multiselectHandler: MultiselectHandler<Contact>
) : ViewModel(),
    ContactsActionListener {

    private val _contacts: MutableLiveData<State> = MutableLiveData()
    val contacts: LiveData<State> = _contacts

    private val _multiselectModeLiveData: MutableLiveData<Int> = MutableLiveData()
    val multiselectModeLiveData: LiveData<Int> = _multiselectModeLiveData

    init {
        viewModelScope.launch {
            multiselectHandler.setItemsFlow(viewModelScope, repositoryContacts.getContacts())
            val combinedFlow = combine(
                repositoryContacts.getContacts(),
                multiselectHandler.listen(), ::merge
            )
            combinedFlow.collectLatest {
                _contacts.value = it
            }
        }
    }

    override fun onContactDelete(contact: ContactListItem) {
        repositoryContacts.deleteContact(contact.originContact.id)
    }

    override fun onContactRestore(contact: ContactListItem) {
        repositoryContacts.addContact(contact.originContact)
    }

    override fun onContactAdd(contact: ContactListItem) {
        repositoryContacts.addContact(contact.originContact)
    }

    override fun onContactToggle(contact: ContactListItem) {
        val multiselectMode = multiselectHandler.toggle(contact.originContact)
        onMultiSelectMode(multiselectMode)
    }

    override fun onMultiSelectMode(multiselectMode: Int) {
        _multiselectModeLiveData.value = multiselectMode
    }

    override fun deleteSelectedContacts() {
        for (contact in contacts.value!!.contactList) {
            if (contact.isChecked) {
                repositoryContacts.deleteContact(contact.id)
            }
        }
    }

    private fun merge(
        cats: List<Contact>,
        multiChoiceState: MultiselectState<Contact>
    ): State {
        return State(
            contactList = cats.map { cat ->
                ContactListItem(cat, multiChoiceState.isChecked(cat))
            },
            multiselectMode = multiChoiceState.totalCheckedCount > 0
        )
    }

    class State(
        val contactList: List<ContactListItem>,
        val multiselectMode: Boolean
    )
}

