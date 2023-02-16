package com.example.firebase.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)

        binding.signIn.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_signInFragment)
        }

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_registrationFragment)
        }

        return binding.root
    }
}