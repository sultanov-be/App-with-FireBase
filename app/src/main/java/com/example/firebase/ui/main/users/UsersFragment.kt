package com.example.firebase.ui.main.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapters.ChatClickHandler
import com.example.firebase.adapters.UserAdapter
import com.example.firebase.databinding.FragmentUsersBinding
import com.example.firebase.ui.main.MainFragmentDirections

class UsersFragment : Fragment(), ChatClickHandler {
    private lateinit var binding: FragmentUsersBinding
    private val viewModel : UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater)

        viewModel.getUsers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listUser.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adapter = UserAdapter(it)
                recyclerUsers.adapter = adapter
                adapter.setInterface(this@UsersFragment)
                progressBar.visibility = GONE
                recyclerUsers.visibility = VISIBLE
            }
        }
    }

    override fun clickedUser(id: String) {
        findNavController().navigate(MainFragmentDirections.goToMessage(id))
    }
}