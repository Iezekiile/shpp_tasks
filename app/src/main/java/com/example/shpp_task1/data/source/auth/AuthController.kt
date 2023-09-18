package com.example.shpp_task1.data.source.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import com.example.shpp_task1.presentation.fragments.auth.register.MIN_PASSWORD_LENGTH
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.enums.ValidationPasswordErrors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class for managing user authentication
 */
@Singleton
class AuthController @Inject constructor() : AuthManager {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        private lateinit var sharedPreferences: SharedPreferences
        fun setContext(context: Context) {
            this.context = context
            sharedPreferences =
                context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
        }
    }

    override fun setUserMemorized(isMemorized: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(Constants.IS_USER_MEMORIZED, isMemorized)
            apply()
        }
    }

    override fun getUserMemorized(): Boolean {
        return sharedPreferences.getBoolean(Constants.IS_USER_MEMORIZED, false)
    }

    override fun validateUserData(email: String, password: String): Boolean {
        return (validateEmail(email) && (validatePassword(password) == ValidationPasswordErrors.SUCCESS))
    }

    override fun hasUserData(): Boolean {
        val savedEmail = sharedPreferences.getString(Constants.EMAIL, "null")
        val savedPassword = sharedPreferences.getString(Constants.PASSWORD, "null")
        return savedEmail != "null" && savedPassword != "null"
    }

    override fun saveUserData(email: String, password: String): Boolean {
        return if (!hasUserData()) {
            if (validateUserData(email, password)) {
                sharedPreferences.edit().apply {
                    putString(Constants.EMAIL, email)
                    putString(Constants.PASSWORD, password)
                    apply()
                }
            }
            true
        } else false
    }

    override fun authenticateUser(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString(Constants.EMAIL, "null")
        val savedPassword = sharedPreferences.getString(Constants.PASSWORD, "null")
        return savedEmail == email && savedPassword == password
    }

    override fun deleteUserData() {
        sharedPreferences.edit().clear().apply()
    }

    override fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun validatePassword(password: String): ValidationPasswordErrors {
        return when {
            !hasMinimumLength(password) -> ValidationPasswordErrors.SHORT_PASSWORD
            !hasLowerCase(password) -> ValidationPasswordErrors.ONLY_UPPERCASE
            !hasUpperCase(password) -> ValidationPasswordErrors.ONLY_LOWERCASE
            !hasDigit(password) -> ValidationPasswordErrors.LACK_DIGIT
            hasWhitespace(password) -> ValidationPasswordErrors.SPACE_IN_PASSWORD
            else -> ValidationPasswordErrors.SUCCESS
        }
    }

    private fun hasLowerCase(password: String?): Boolean {
        val lowercasePattern = Regex(Constants.LEAST_ONE_LOWERCASE_LETTER)
        return lowercasePattern.matches(password!!)
    }

    private fun hasUpperCase(password: String?): Boolean {
        val uppercasePattern = Regex(Constants.LEAST_ONE_UPPERCASE_LETTER)
        return uppercasePattern.matches(password!!)
    }

    private fun hasDigit(password: String?): Boolean {
        val digitPattern = Regex(Constants.LEAST_ONE_DIGIT)
        return digitPattern.matches(password!!)
    }

    private fun hasWhitespace(password: String?): Boolean {
        val whitespacePattern = Regex(Constants.HAS_WHITESPACE)
        return whitespacePattern.matches(password!!)
    }

    private fun hasMinimumLength(password: String?): Boolean {
        return !password.isNullOrBlank() && password.length >= MIN_PASSWORD_LENGTH
    }
}