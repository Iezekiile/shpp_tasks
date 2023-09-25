package com.example.shpp_task1.multiselect

import com.example.shpp_task1.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
/**
 * Multiselect handler for contacts
 */
interface MultiselectHandler<T> {

    fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<T>>)
    fun listen(): Flow<MultiselectState<T>>
    fun toggle(item: Contact): Boolean

}