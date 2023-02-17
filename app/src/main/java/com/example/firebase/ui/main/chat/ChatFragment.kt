package com.example.firebase.ui.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapters.ChatClickHandler
import com.example.firebase.adapters.UserAdapter
import com.example.firebase.databinding.FragmentChatBinding
import com.example.firebase.ui.main.MainFragmentDirections

class ChatFragment : Fragment(), ChatClickHandler {
    private lateinit var binding: FragmentChatBinding
    private val viewModel : ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentChatBinding.inflate(layoutInflater)

        viewModel.getActiveChats()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listChats.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adapter = UserAdapter(it)
                recyclerUsers.adapter = adapter
                adapter.setInterface(this@ChatFragment)
                progressBar.visibility = View.GONE
                recyclerUsers.visibility = View.VISIBLE
            }
        }
    }

    override fun clickedUser(id: String) {
        findNavController().navigate(MainFragmentDirections.goToMessage(id))
    }
}