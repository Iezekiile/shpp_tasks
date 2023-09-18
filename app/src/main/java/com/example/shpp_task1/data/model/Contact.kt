package com.example.shpp_task1.data.model

/*
* Data class for contact
 */
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact(
    val id: String? = UUID.randomUUID().toString(),
    val avatar: String?,
    val username: String?,
    val career: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthday: String?
) : Parcelable