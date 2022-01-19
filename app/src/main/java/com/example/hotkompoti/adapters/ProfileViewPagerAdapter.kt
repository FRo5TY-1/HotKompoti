package com.example.hotkompoti.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hotkompoti.fragments.InformationFragment
import com.example.hotkompoti.fragments.PostsFragment
import com.example.hotkompoti.fragments.ProfileFragment

class ProfileViewPagerAdapter(activity: ProfileFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InformationFragment()
            1 -> PostsFragment()
            else -> InformationFragment()
        }
    }

}