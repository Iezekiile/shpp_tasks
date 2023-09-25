package com.example.shpp_task1.multiselect

import com.example.shpp_task1.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Multiselect handler for contacts
 */
class ContactsMultiselectHandler : MultiselectHandler<Contact>, MultiselectState<Contact> {

    private val checkedIds = HashSet<String>()
    private var items: List<Contact> = emptyList()
    private val stateFlow = MutableStateFlow(OnChanged())


    override fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<Contact>>) {
        coroutineScope.launch {
            itemsFlow.collectLatest { list ->
                items = list
                removeDeletedCats(list)
                notifyUpdates()
            }
        }
    }


    private fun removeDeletedCats(contacts: List<Contact>) {
        val existingIds = HashSet(contacts.map { it.id })
        val iterator = checkedIds.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next()
            if (!existingIds.contains(id)) {
                iterator.remove()
            }
        }
    }


    override fun listen(): Flow<MultiselectState<Contact>> {
        return stateFlow.map { this }
    }


    private fun exists(item: Contact): Boolean {
        return items.any { it.id == item.id }
    }


    override fun toggle(item: Contact): Boolean {
        if (!exists(item)) return isMultiselect
        if (isChecked(item)) checkedIds.remove(item.id) else checkedIds.add(item.id!!)
        notifyUpdates()
        return isMultiselect
    }

    override val isMultiselect: Boolean
        get() = checkedIds.size > 0

    override fun isChecked(item: Contact): Boolean {
        return checkedIds.contains(item.id)
    }

    private fun notifyUpdates() {
        stateFlow.value = OnChanged()
    }

    private class OnChanged
}