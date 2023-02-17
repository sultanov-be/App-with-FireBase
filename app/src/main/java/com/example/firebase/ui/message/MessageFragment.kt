package com.example.firebase.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.firebase.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private val viewModel: MessageViewModel by viewModels()
    private lateinit var binding: FragmentMessageBinding
    private var args: MessageFragmentArgs? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)

        args = MessageFragmentArgs.fromBundle(requireArguments())

        binding.id.text = args!!.id

        return binding.root
    }
}