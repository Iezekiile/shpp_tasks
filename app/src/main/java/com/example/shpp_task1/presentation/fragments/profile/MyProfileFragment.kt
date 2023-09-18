package com.example.shpp_task1.presentation.fragments.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentMyProfileBinding
import com.example.shpp_task1.presentation.activities.AuthActivity
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerProvider
import com.example.shpp_task1.presentation.fragments.viewpager.ViewPagerRequireActivityProvider
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.viewBinding
import java.util.Locale

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {
    private val binding by viewBinding<FragmentMyProfileBinding>()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewPager: ViewPager2
    private lateinit var requireActivity: AppCompatActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = parentFragment as ViewPagerProvider?
        val activityProvider = parentFragment as ViewPagerRequireActivityProvider
        viewPager = provider!!.getViewPager()
        requireActivity = activityProvider.getRequireActivity()
        sharedPreferences =
            requireActivity.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
        setProfileData()
        setViewMyContactsListener()
        setLogOutListener()
    }

    private fun setProfileData() {
        val fullName = parseEmailToName(sharedPreferences.getString(Constants.EMAIL, "")!!)
        val fullNameText = resources.getString(R.string.full_name, fullName.first, fullName.second)
        binding.fullName.text = fullNameText
    }

    private fun setViewMyContactsListener() {
        binding.contactsButton.setOnClickListener {
            viewPager.currentItem = 1
        }
    }

    private fun setLogOutListener() {
        binding.logOutButton.setOnClickListener {
            val sharedPreferences =
                requireActivity.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun parseEmailToName(email: String): Pair<String, String> {
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