package com.example.shpp_task1.presentation.fragments.auth.vm

import com.example.shpp_task1.utils.enums.ValidationPasswordErrors

interface AuthAction {
    fun onRegister(email: String, password: String): Boolean
    fun onInputEmail(email: String): Boolean
    fun onInputPassword(password: String): ValidationPasswordErrors
    fun onLogin(email: String, password: String): Boolean
    fun autoLogin(email: String?, password: String?): Boolean
    fun onRememberMe(isChecked: Boolean)
}