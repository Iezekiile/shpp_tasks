package com.example.shpp_task1.presentation.fragments.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shpp_task1.R
import com.example.shpp_task1.data.source.auth.AuthController
import com.example.shpp_task1.databinding.FragmentLoginBinding
import com.example.shpp_task1.presentation.activities.MainActivity
import com.example.shpp_task1.presentation.fragments.auth.vm.AuthViewModel
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.presentation.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding<FragmentLoginBinding>()
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuthController.setContext(requireContext())
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
        autoLogin()
        setActionListeners()
    }

    private fun setActionListeners() {
        setButtonLoginListeners()
        setSignUpButtonListener()
        setEmailInputListeners()
    }

    private fun setButtonLoginListeners() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailTextInput.text.toString()
            val password = binding.passwordTextInput.text.toString()
            if (viewModel.onLogin(email, password)) {
                viewModel.onRememberMe(binding.checkBoxRememberMe.isChecked)
                startActivity(Intent(requireActivity(), MainActivity::class.java))      //todo DRY
                requireActivity().finish()
            } else {
                Toast.makeText(context, getText(R.string.wrong_email_or_password), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSignUpButtonListener() {
        binding.textSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToAuthFragment()
            findNavController().navigate(action)
        }
    }

    // TODO: delete this code after completing level 4
    ////////////////////////////////////////////////////////////
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

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    ////////////////////////////////////////////////////////////
    private fun autoLogin() {
        if (viewModel.autoLogin(
                sharedPreferences.getString(Constants.EMAIL, "null"),
                sharedPreferences.getString(Constants.PASSWORD, "null")
        )) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }
}