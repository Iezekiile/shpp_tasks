package com.example.shpp_task1.presentation.fragments.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentMyProfileBinding
import com.example.shpp_task1.presentation.activities.AuthActivity
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerFragment
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.presentation.utils.viewBinding
import java.util.Locale

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private val binding by viewBinding<FragmentMyProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfileData()
        setViewMyContactsListener()
        setLogOutListener()
    }

    private fun setProfileData() {
        val sp = (requireActivity() as AppCompatActivity).getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
        val fullName = parseEmailToName(sp.getString(Constants.EMAIL, "")!!)
        val fullNameText = resources.getString(R.string.full_name, fullName.first, fullName.second)
        binding.fullName.text = fullNameText
    }

    private fun setViewMyContactsListener() {
        binding.contactsButton.setOnClickListener {
            (parentFragment as ViewPagerFragment).viewPager.currentItem = 1
        }
    }

    private fun setLogOutListener() {
        binding.logOutButton.setOnClickListener {
            val sharedPreferences =
                (requireActivity() as AppCompatActivity).getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun parseEmailToName(email: String): Pair<String, String> {     //todo decompose to Object
        val parts = email.split("@").firstOrNull()?.split(".")
        val firstName = parts?.getOrNull(0)
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            ?: ""
        val lastName = if ((parts?.size ?: 0) > 1) {
            parts?.getOrNull(1)
                ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                ?: ""
        } else {
            ""
        }
        return Pair(firstName, lastName)
    }
}