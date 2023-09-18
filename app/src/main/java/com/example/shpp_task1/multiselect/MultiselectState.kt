package com.example.shpp_task1.multiselect
/**
 * State for multiselect
 */
interface MultiselectState<T> {

    fun isChecked(item: T): Boolean

    val totalCheckedCount: Int
}