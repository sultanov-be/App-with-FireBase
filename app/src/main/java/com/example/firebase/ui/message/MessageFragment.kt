package com.example.firebase.ui.message

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.R
import com.example.firebase.adapters.MessageAdapter
import com.example.firebase.databinding.FragmentMessageBinding
import com.google.firebase.auth.FirebaseAuth

class MessageFragment : Fragment() {
    private val viewModel: MessageViewModel by viewModels()
    private lateinit var binding: FragmentMessageBinding
    private var args: MessageFragmentArgs? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val fUser = FirebaseAuth.getInstance().currentUser!!.uid

        args = MessageFragmentArgs.fromBundle(requireArguments())

        viewModel.getData(args!!.id)
        viewModel.getMessages(fUser, args!!.id)

        showNickname()
        showMessages()

        sendBtn.setOnClickListener {
            if (emailInputText.text.toString() != "") {
                viewModel.sendMessage(
                    emailInputText.text.toString(),
                    args!!.id,
                    fUser
                )

                emailInputText.text = "".toEditable()
            }
        }
    }

    private fun showNickname() = with(binding) {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            } else {
                username.text = getString(R.string.user, it.username)
            }
        }
    }

    private fun showMessages() = with(binding) {
        viewModel.listChat.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerChat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adapter = MessageAdapter(it)
                recyclerChat.adapter = adapter
                showViews()
            }
        }
    }

    private fun showViews() = with(binding) {
        progressbar.visibility = GONE
        tb.visibility = VISIBLE
        emailInputLayout.visibility = VISIBLE
        sendBtn.visibility = VISIBLE
        recyclerChat.visibility = VISIBLE
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}