package com.example.shpp_task1.data.model

/**
 * Data class for contact list item
 * @param originContact - origin contact
 * @param isChecked - is contact checked
 */
data class ContactListItem(
    val originContact: Contact,
    val isChecked: Boolean
) {
    val id: String? get() = originContact.id
    val username: String? get() = originContact.username
    val career: String? get() = originContact.career
    val email: String? get() = originContact.email
    val phone: String? get() = originContact.phone
    val address: String? get() = originContact.address
    val birthday: String? get() = originContact.birthday
    val avatar: String? get() = originContact.avatar
}