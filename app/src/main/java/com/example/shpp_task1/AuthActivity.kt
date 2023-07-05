package com.example.shpp_task1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.shpp_task1.databinding.ActivityAuthBinding
import com.google.android.material.textfield.TextInputEditText

class AuthActivity : AppCompatActivity() {
    private val MIN_PASSWORD_LENGTH = 8
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val email = sharedPreferences.getString("email", "")
        if (email.isNullOrEmpty()) {
            setupActionListeners(editor)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }

    /**
     * Sets action listeners for all clickable elements in an activity
     * @param editor Used to save data
     */
    private fun setupActionListeners(editor: SharedPreferences.Editor) {
        val intent = Intent(this, MainActivity::class.java)

        binding.buttonRegister.setOnClickListener {
            val password: String = binding.passwordTextInput.text.toString()
            val email: String = binding.emailTextInput.text.toString()
            if (validatePassword(password, binding.passwordTextInput) &&
                validateEmail(email, binding.emailTextInput)
            ) {
                intent.putExtra("password", password)
                intent.putExtra("email", email)
                startActivity(intent)
                if (binding.checkBoxRememberMe.isChecked) {
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()
                }
            }
        }

        binding.passwordTextInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                hideKeyboard(binding.passwordTextInput)
            val password: String = binding.passwordTextInput.text.toString()
            validatePassword(password, binding.passwordTextInput)
            false
        }

        binding.emailTextInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                hideKeyboard(binding.emailTextInput)
            val email: String = binding.emailTextInput.text.toString()
            validateEmail(email, binding.emailTextInput)
            false
        }
    }

    /**
     * Validates the email format using the provided regular expression pattern.
     * If the email is not valid, sets an error message on the editText.
     *
     * @param email The email string to validate.
     * @param editText The TextInputEditText to display the error message.
     * @return true if the email is valid, false otherwise.
     */
    private fun validateEmail(email: String?, editText: TextInputEditText): Boolean {
        val validate: Boolean = Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
        if (!validate) {
            editText.error = getString(R.string.incorrect_email)
        }
        return validate
    }

    /**
     * Validates the password based on various criteria.
     * Checks for minimum length, lowercase, uppercase, digit, and whitespace.
     * Sets an appropriate error message on the editText if any criteria fails.
     *
     * @param password The password string to validate.
     * @param editText The TextInputEditText to display the error message.
     * @return true if the password is valid, false otherwise.
     */
    private fun validatePassword(password: String?, editText: TextInputEditText): Boolean {
        val errorMsg: String = when {
            !hasMinimumLength(password) -> getString(R.string.short_password_error)
            !hasLowerCase(password) -> getString(R.string.only_uppercase_error)
            !hasUpperCase(password) -> getString(R.string.only_lowercase_error)
            !hasDigit(password) -> getString(R.string.digit_lack_error)
            hasWhitespace(password) -> getString(R.string.space_on_password_error)
            else -> return true
        }

        editText.error = errorMsg
        return false
    }

    /**
     * Checks if the password contains at least one lowercase character.
     *
     * @param password The password string to check.
     * @return true if the password contains at least one lowercase character, false otherwise.
     */
    private fun hasLowerCase(password: String?): Boolean {
        val lowercasePattern = Regex(".*[a-z].*")
        return lowercasePattern.matches(password!!)
    }

    /**
     * Checks if the password contains at least one uppercase character.
     *
     * @param password The password string to check.
     * @return true if the password contains at least one uppercase character, false otherwise.
     */
    private fun hasUpperCase(password: String?): Boolean {
        val uppercasePattern = Regex(".*[A-Z].*")
        return uppercasePattern.matches(password!!)
    }


    /**
     * Checks if the password contains at least one digit.
     * @param password The password string to check.
     * @return true if the password contains at least one digit, false otherwise.
     */
    private fun hasDigit(password: String?): Boolean {
        val digitPattern = Regex(".*\\d.*")
        return digitPattern.matches(password!!)
    }

    /**
     * Checks if the password contains any whitespace characters.
     *
     * @param password The password string to check.
     * @return true if the password contains whitespace characters, false otherwise.
     */
    private fun hasWhitespace(password: String?): Boolean {
        val whitespacePattern = Regex(".*\\s.*")
        return whitespacePattern.matches(password!!)
    }

    /**
     * Checks if the password meets the minimum length requirement.
     *
     * @param password The password string to check.
     * @return true if the password meets the minimum length requirement, false otherwise.
     */
    private fun hasMinimumLength(password: String?): Boolean {
        return !password.isNullOrBlank() && password.length >= MIN_PASSWORD_LENGTH
    }

    /**
     * Hides the soft keyboard for the specified editText.
     *
     * @param editText The TextInputEditText from which to hide the keyboard.
     */
    private fun hideKeyboard(editText: TextInputEditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}