package com.example.shpp_task1.presentation.fragments.viewpager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentViewPagerBinding
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.example.shpp_task1.utils.viewBinding
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment(R.layout.fragment_view_pager), ViewPagerProvider,
    ViewPagerRequireActivityProvider {

    private val binding by viewBinding<FragmentViewPagerBinding>()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        viewPager = binding.viewPager
        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initTabLayout() {
        if (FeatureFlags.USE_TAB_LAYOUT) {
            TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.profile)
                    1 -> getString(R.string.contacts)
                    else -> throw IllegalStateException("Bug tab!")
                }
            }.attach()
        } else {
            binding.tabLayout.visibility = View.GONE
        }
    }

    override fun getViewPager(): ViewPager2 {
        return viewPager
    }

    override fun getRequireActivity(): AppCompatActivity {
        return requireActivity() as AppCompatActivity
    }
}