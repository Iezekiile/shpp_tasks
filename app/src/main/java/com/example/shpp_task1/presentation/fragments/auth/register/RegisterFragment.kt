package com.example.shpp_task1.presentation.fragments.auth.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentRegisterBinding
import com.example.shpp_task1.presentation.activities.MainActivity
import com.example.shpp_task1.presentation.fragments.auth.vm.AuthViewModel
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.enums.ValidationPasswordErrors
import com.example.shpp_task1.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

const val MIN_PASSWORD_LENGTH = 8

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding<FragmentRegisterBinding>()
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
        setActionListeners()
    }

    private fun setActionListeners() {
        setButtonRegisterListeners()
        setEmailInputListeners()
        setPasswordInputListeners()
    }

    private fun setPasswordInputListeners() {
        binding.passwordTextInput.setOnEditorActionListener { _, _, _ ->
            val password: String = binding.passwordTextInput.text.toString()
            val errorText = getPasswordError(viewModel.onInputPassword(password))
            binding.passwordInputLayout.helperText = errorText
            hideKeyboard()
            true
        }
    }

    private fun getPasswordError(error: ValidationPasswordErrors): String? {
        val index = error.ordinal
        return when (index) {
            0 -> getString(R.string.short_password_error)
            1 -> getString(R.string.only_uppercase_error)
            2 -> getString(R.string.only_lowercase_error)
            3 -> getString(R.string.digit_lack_error)
            4 -> getString(R.string.space_in_password_error)
            else -> null
        }
    }

    private fun setEmailInputListeners() {
        binding.emailTextInput.setOnEditorActionListener { _, _, _ ->
            val email: String = binding.emailTextInput.text.toString()
            if (!viewModel.onInputEmail(email)) {
                binding.emailInputLayout.helperText = getString(R.string.incorrect_email)
                hideKeyboard()
            }
            true
        }
    }

    private fun setButtonRegisterListeners() {
        binding.buttonRegister!!.setOnClickListener {
            val password: String = binding.passwordTextInput.text.toString()
            val email: String = binding.emailTextInput.text.toString()
            if (viewModel.onRegister(email, password)) {
                viewModel.onRememberMe(binding.checkBoxRememberMe.isChecked)
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}