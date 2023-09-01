package com.example.shpp_task1.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
* Data class for contact
 */
@Parcelize
data class Contact(
    val id: Long,
    val avatar: String?,
    val username: String?,
    val career: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthday: String?
): Parcelable