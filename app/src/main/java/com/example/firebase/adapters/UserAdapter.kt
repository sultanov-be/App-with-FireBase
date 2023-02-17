package com.example.firebase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.UserItemBinding
import com.example.firebase.firebase.User

class UserAdapter constructor(var list: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    lateinit var userClicked: ChatClickHandler

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val user = list[position]

        holder.itemView.setOnClickListener {
            userClicked.clickedUser(user.id!!)
        }

        username.text = user.username
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setInterface(userClicked: ChatClickHandler) {
        this.userClicked = userClicked
    }
}