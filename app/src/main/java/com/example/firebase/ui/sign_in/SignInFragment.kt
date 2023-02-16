package com.example.firebase.ui.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private val viewModel : SignInViewModel by viewModels()
    private lateinit var binding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener { findNavController().popBackStack() }

        signUpButton.setOnClickListener {
            if (!emailInputText.text.isNullOrEmpty() && !passwordInputText.text.isNullOrEmpty()) {
                viewModel.signIn(
                    emailInputText.text.toString(),
                    passwordInputText.text.toString()
                )
            }
        }

        viewModel.isLogged.observe(viewLifecycleOwner) {
            when(it) {
                true -> findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                else -> Toast.makeText(requireContext(), "Login or password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}