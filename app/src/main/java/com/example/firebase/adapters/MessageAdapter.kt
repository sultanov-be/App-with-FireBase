package com.example.firebase.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.databinding.ChatItemBinding
import com.example.firebase.firebase.Chat
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter constructor(
    var list: List<Chat>
) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.text.background = if (viewType == 0) {
            ContextCompat.getDrawable(parent.context, R.drawable.right_bg)
        } else
            ContextCompat.getDrawable(parent.context, R.drawable.left_bg)

        binding.text.layoutParams = setParams(viewType)


        return ViewHolder(binding)
    }

    private fun setParams(viewType: Int): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = if (viewType == 0) {
                Gravity.END
            } else Gravity.START
        }

        return params
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val chat = list[position]



        text.text = chat.message
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (list[position].sender == FirebaseAuth.getInstance().currentUser!!.uid) {
            1
        } else 0
    }
}