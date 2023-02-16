package com.example.firebase.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private val viewModel: RegistrationViewModel by viewModels()
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        signUpButton.setOnClickListener {
            if (!emailInputText.text.isNullOrEmpty() && !passwordInputText.text.isNullOrEmpty() && !nickInputText.text.isNullOrEmpty()) {
                viewModel.register(
                    nickInputText.text.toString(),
                    emailInputText.text.toString(),
                    passwordInputText.text.toString()
                )
            }
        }

        backButton.setOnClickListener { findNavController().popBackStack() }

        viewModel.isRegistered.observe(viewLifecycleOwner) {
            //TODO(Handle situation with navigation)
            when(it) {
                true -> findNavController().navigate(R.id.action_registrationFragment_to_signInFragment)
                else -> Toast.makeText(requireContext(), "Cannot to register a user", Toast.LENGTH_SHORT).show()
            }
        }
    }
}