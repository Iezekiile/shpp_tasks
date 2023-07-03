package com.example.shpp_task1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.TextUtils
import android.transition.Explode
import android.transition.Fade
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.shpp_task1.databinding.ActivityAuthBinding
import com.google.android.material.textfield.TextInputEditText

@Suppress("NAME_SHADOWING")
class AuthActivity : AppCompatActivity() {
    private val MIN_PASSWORD_LENGTH = 8
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val intent = Intent(this, MainActivity::class.java)
        val email = sharedPreferences.getString("email", "")

        if(email.equals("")){
            setContentView(view)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            actionListeners(editor, intent)
        } else {
            intent.putExtra("email", email)
            startActivity(intent)
        }

    }

    private fun actionListeners(editor: Editor, intent: Intent){

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
        }

        binding.emailTextInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                hideKeyboard(binding.emailTextInput)
            val email: String = binding.emailTextInput.text.toString()
            validateEmail(email, binding.emailTextInput)
        }
    }

    private fun validateEmail(email: String?, editText: TextInputEditText): Boolean {
        val validate: Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!validate) {
            editText.error = getString(R.string.incorrect_email)
        }
        return validate
    }

    private fun validatePassword(password: String?, editText: TextInputEditText): Boolean {

        val errorMsg: String = if (!hasMinimumLength(password)) {
            getString(R.string.short_password_error)
        } else if (!hasLowerCase(password)) {
            getString(R.string.only_uppercase_error)
        } else if (!hasUpperCase(password)) {
            getString(R.string.only_lowercase_error)
        } else if (!hasDigit(password)) {
            getString(R.string.digit_lack_error)
        } else if (hasWhitespace(password)) {
            getString(R.string.space_on_password_error)
        } else return true

        editText.error = errorMsg
        return false
    }

    private fun hasLowerCase(password: String?): Boolean {
        val lowercasePattern = Regex(".*[a-z].*")
        return lowercasePattern.matches(password!!)
    }

    private fun hasUpperCase(password: String?): Boolean {
        val uppercasePattern = Regex(".*[A-Z].*")
        return uppercasePattern.matches(password!!)
    }

    private fun hasDigit(password: String?): Boolean {
        val digitPattern = Regex(".*\\d.*")
        return digitPattern.matches(password!!)
    }

    private fun hasWhitespace(password: String?): Boolean {
        val whitespacePattern = Regex(".*\\s.*")
        return whitespacePattern.matches(password!!)
    }

    private fun hasMinimumLength(password: String?): Boolean {
        if (TextUtils.isEmpty(password)) {
            return false
        }
        return password!!.length >= MIN_PASSWORD_LENGTH
    }

    private fun hideKeyboard(editText: TextInputEditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}