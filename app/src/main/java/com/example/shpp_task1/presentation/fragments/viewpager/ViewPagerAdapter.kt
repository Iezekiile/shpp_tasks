package com.example.shpp_task1.presentation.fragments.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shpp_task1.presentation.fragments.contacts.MyContactsFragment
import com.example.shpp_task1.presentation.fragments.profile.MyProfileFragment

class ViewPagerAdapter(fragmentActivity: Fragment) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2    //todo const // enum
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MyProfileFragment()
        } else {
            MyContactsFragment()
        }
    }
}
