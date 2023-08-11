package com.example.shpp_task1.model

/*
* Data class for contact
 */
data class Contact(
    val id: Long,
    val avatar: String,
    val username: String,
    val career: String,
    val email: String,
    val phone: String,
    val address: String,
    val birthday: String
)
