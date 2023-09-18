package com.example.shpp_task1.presentation.fragments.auth.vm

import androidx.lifecycle.ViewModel
import com.example.shpp_task1.data.source.auth.AuthController
import com.example.shpp_task1.utils.enums.ValidationPasswordErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for authentication
 */
@HiltViewModel
class AuthViewModel @Inject constructor(private val authController: AuthController) : ViewModel(),
    AuthAction {

    override fun onRegister(email: String, password: String): Boolean {
        return authController.saveUserData(email, password)
    }

    override fun onLogin(email: String, password: String): Boolean {
        return authController.authenticateUser(email, password)
    }

    override fun autoLogin(email: String?, password: String?): Boolean {
        return if (email != null && password != null) {
            authController.getUserMemorized()
        } else false
    }

    override fun onRememberMe(isChecked: Boolean) {
        authController.setUserMemorized(isChecked)
    }

    override fun onInputEmail(email: String): Boolean {
        return authController.validateEmail(email)
    }

    override fun onInputPassword(password: String): ValidationPasswordErrors {
        return authController.validatePassword(password)
    }
}