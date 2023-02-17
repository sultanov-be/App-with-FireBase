package com.example.firebase.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.firebase.R
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
        viewModel.getData(args!!.id)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.setOnClickListener {
            if (emailInputText.text.toString() != "") {
                //TODO(send message)
            }
        }

        showNickname()
    }

    private fun showNickname() = with(binding) {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            } else {
                username.text = getString(R.string.user, it.username)
                showViews()
            }
        }
    }

    private fun showViews() = with(binding) {
        progressbar.visibility = GONE
        tb.visibility = VISIBLE
        emailInputLayout.visibility = VISIBLE
        sendBtn.visibility = VISIBLE
    }
}