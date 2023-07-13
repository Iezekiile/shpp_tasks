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

const val MIN_PASSWORD_LENGTH = 8

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        autologin()
        setContentView(binding.root)
    }

    /**
     * Checks for previously entered data, if any starts the profile,
     * otherwise sets event listeners for the current activity
     */
    private fun autologin() {
        val email = sharedPreferences.getString(Constants.EMAIL, "")
        if (email.isNullOrEmpty()) {
            setActionListeners()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.EMAIL, email)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    /**
     * Sets action listeners for all clickable elements in an activity
     */
    private fun setActionListeners() {
        setButtonRegisterListeners()
        setEmailInputListeners()
        setPasswordInputListeners()
    }

    /**
     * Sets action listeners for password text input
     */
    private fun setPasswordInputListeners() {
        binding.passwordTextInput.setOnEditorActionListener { _, actionId, _ ->
            setKeyboardHidding(actionId)
            val password: String = binding.passwordTextInput.text.toString()
            binding.passwordInputLayout.helperText = validatePassword(password)
            false
        }
    }

    /**
     * Sets action listeners for email text input
     */
    private fun setEmailInputListeners() {
        binding.emailTextInput.setOnEditorActionListener { _, actionId, _ ->
            val email: String = binding.emailTextInput.text.toString()
            validateEmail(email)
            false
        }
    }

    /**
     * Hiding the keyboard after confirming input
     * @param actionId - actionId from another listener
     */
    private fun setKeyboardHidding(actionId: Int){
        if (actionId == EditorInfo.IME_ACTION_DONE)
            hideKeyboard(binding.emailTextInput)
    }


    /**
     * Sets action listeners for register button
     */
    private fun setButtonRegisterListeners() {
        val intent = Intent(this, MainActivity::class.java)
        binding.buttonRegister.setOnClickListener {
            val password: String = binding.passwordTextInput.text.toString()
            val email: String = binding.emailTextInput.text.toString()
            if (validatePassword(password) == null &&
                validateEmail(email)
            ) {
                intent.putExtra(Constants.PASSWORD, password)
                intent.putExtra(Constants.EMAIL, email)
                startActivity(intent)
                if (binding.checkBoxRememberMe.isChecked) {
                    val editor = sharedPreferences.edit()
                    editor.putString(Constants.EMAIL, email)
                    editor.putString(Constants.PASSWORD, password)
                    editor.apply()
                }
            } else {
                binding.passwordInputLayout.helperText = validatePassword(password)
                validateEmail(email)
            }
        }
    }

    /**
     * Validates the email format using the provided regular expression pattern.
     * If the email is not valid, sets an error message on the editText.
     *
     * @param email The email string to validate.
     * @return true if the email is valid, false otherwise.
     */
    private fun validateEmail(email: String?): Boolean {
        val validate: Boolean = Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
        if (!validate) {
            binding.emailInputLayout.helperText = getString(R.string.incorrect_email)
        } else
            binding.emailInputLayout.helperText = null
        return validate
    }

    /**
     * Validates the password based on various criteria if there are matches,
     * returns the text of the corresponding error
     * Checks for minimum length, lowercase, uppercase, digit, and whitespace.
     *
     * @param password The password string to validate.
     * @return error text or null
     */
    private fun validatePassword(password: String?): String? =
        if (!hasMinimumLength(password)) getString(R.string.short_password_error)
        else if (!hasLowerCase(password)) getString(R.string.only_uppercase_error)
        else if (!hasUpperCase(password)) getString(R.string.only_lowercase_error)
        else if (!hasDigit(password)) getString(R.string.digit_lack_error)
        else if (hasWhitespace(password)) getString(R.string.space_on_password_error)
        else null

    /**
     * Checks if the password contains at least one lowercase character.
     *
     * @param password The password string to check.
     * @return true if the password contains at least one lowercase character, false otherwise.
     */
    private fun hasLowerCase(password: String?): Boolean {
        val lowercasePattern = Regex(Constants.LEAST_ONE_LOWERCASE_LETTER)
        return lowercasePattern.matches(password!!)
    }

    /**
     * Checks if the password contains at least one uppercase character.
     *
     * @param password The password string to check.
     * @return true if the password contains at least one uppercase character, false otherwise.
     */
    private fun hasUpperCase(password: String?): Boolean {
        val uppercasePattern = Regex(Constants.LEAST_ONE_UPPERCASE_LETTER)
        return uppercasePattern.matches(password!!)
    }


    /**
     * Checks if the password contains at least one digit.
     * @param password The password string to check.
     * @return true if the password contains at least one digit, false otherwise.
     */
    private fun hasDigit(password: String?): Boolean {
        val digitPattern = Regex(Constants.LEAST_ONE_DIGIT)
        return digitPattern.matches(password!!)
    }

    /**
     * Checks if the password contains any whitespace characters.
     *
     * @param password The password string to check.
     * @return true if the password contains whitespace characters, false otherwise.
     */
    private fun hasWhitespace(password: String?): Boolean {
        val whitespacePattern = Regex(Constants.HAS_WHITESPACE)
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