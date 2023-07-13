package com.example.shpp_task1

interface Constants {
    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val LEAST_ONE_LOWERCASE_LETTER = ".*[a-z].*"
        const val LEAST_ONE_UPPERCASE_LETTER = ".*[A-Z].*"
        const val LEAST_ONE_DIGIT = ".*\\d.*"
        const val HAS_WHITESPACE = ".*\\s.*"
    }
}