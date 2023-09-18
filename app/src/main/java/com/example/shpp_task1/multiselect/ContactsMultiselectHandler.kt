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


    override fun check(item: Contact) {
        if (!exists(item)) return
        checkedIds.add(item.id!!)
        notifyUpdates()
    }


    override fun clear(item: Contact) {
        if (!exists(item)) return
        checkedIds.remove(item.id)
        notifyUpdates()
    }


    private fun exists(item: Contact): Boolean {
        return items.any { it.id == item.id }
    }


    override fun toggle(item: Contact): Int {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
        return totalCheckedCount
    }

    override val totalCheckedCount: Int
        get() = checkedIds.size

    override fun isChecked(item: Contact): Boolean {
        return checkedIds.contains(item.id)
    }

    private fun notifyUpdates() {
        stateFlow.value = OnChanged()
    }

    private class OnChanged
}