package com.example.firebase.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebase.ui.main.chat.ChatFragment
import com.example.firebase.ui.main.users.UsersFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentsNumber = 2

    override fun getItemCount(): Int {
        return fragmentsNumber
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                ChatFragment()
            }
            1 -> {
                UsersFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}