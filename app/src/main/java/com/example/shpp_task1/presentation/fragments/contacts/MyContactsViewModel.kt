package com.example.shpp_task1.presentation.fragments.contacts

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
import com.example.shpp_task1.presentation.fragments.contacts.interfaces.ContactsActionListener
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
class MyContactsViewModel @Inject constructor(
    private val repositoryContacts: RepositoryContacts,
    @ContactsMultiselect private val multiselectHandler: MultiselectHandler<Contact>
) : ViewModel() {

    private val _contacts: MutableLiveData<MyContactsState> = MutableLiveData()
    val contacts: LiveData<MyContactsState> = _contacts

    private val _multiselectMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val multiselectMode: LiveData<Boolean> = _multiselectMode

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

    fun onContactDelete(contactItem: ContactListItem) {
        repositoryContacts.deleteContact(contactItem.originContact.id)
    }

    fun onContactClick(contactItem: ContactListItem) {
        return
    }

    fun onLongClick(contactItem: ContactListItem) {
        return
    }
    fun onContactRestore(contactItem: ContactListItem) {
        repositoryContacts.addContact(contactItem.originContact)
    }

    fun onContactAdd(contactItem: ContactListItem) {
        repositoryContacts.addContact(contactItem.originContact)
    }

    fun onContactToggle(contactItem: ContactListItem) {
        val multiselectMode = multiselectHandler.toggle(contactItem.originContact)
        onMultiSelectMode(multiselectMode)
    }

    fun onMultiSelectMode(multiselectMode: Boolean) {
        _multiselectMode.postValue(multiselectMode)
    }

    fun deleteSelectedContacts() {
        for (contact in contacts.value!!.contactList) {
            if (contact.isChecked) {
                repositoryContacts.deleteContact(contact.id)
            }
        }
    }

    private fun merge(
        cats: List<Contact>,
        multiChoiceState: MultiselectState<Contact>
    ): MyContactsState {
        return MyContactsState(
            contactList = cats.map { cat ->
                ContactListItem(cat, multiChoiceState.isChecked(cat))
            },
            multiselectMode = multiChoiceState.isMultiselect
        )
    }


}

