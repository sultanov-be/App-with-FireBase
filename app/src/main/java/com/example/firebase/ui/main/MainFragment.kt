package com.example.firebase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.adapters.ViewPagerAdapter
import com.example.firebase.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserNickname()

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                tbText.text = it.username
            }
        }

        tbLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.startFragment)
        }

        viewpager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tablayout, viewpager) {tab, position ->
            when(position) {
                0 -> tab.text = "Chats"
                1 -> tab.text = "Users"
            }
        }.attach()
    }
}