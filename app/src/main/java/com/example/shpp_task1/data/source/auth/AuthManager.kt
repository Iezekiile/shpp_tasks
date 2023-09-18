package com.example.shpp_task1.data.source.auth

import com.example.shpp_task1.utils.enums.ValidationPasswordErrors

/**
 * Interface for auth manager
 */
interface AuthManager {
    fun validateUserData(email: String, password: String): Boolean
    fun saveUserData(email: String, password: String): Boolean
    fun authenticateUser(email: String, password: String): Boolean
    fun deleteUserData()
    fun validateEmail(email: String): Boolean
    fun validatePassword(password: String): ValidationPasswordErrors
    fun hasUserData(): Boolean

    //temporary
    fun setUserMemorized(isMemorized: Boolean)
    fun getUserMemorized(): Boolean
}