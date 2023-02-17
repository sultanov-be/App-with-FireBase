package com.example.firebase.ui.main.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.firebase.Chat
import com.example.firebase.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val listChats = MutableLiveData<ArrayList<User>>()

    fun getActiveChats() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Chats")

        viewModelScope.launch(Dispatchers.IO) {
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val fireList = ArrayList<Chat>()

                    val children = dataSnapshot.children

                    children.forEach {
                        it.getValue(Chat::class.java)?.let { it1 ->
                            if (it1.sender == currentUser!!.uid) fireList.add(it1)
                            if (it1.receiver == currentUser.uid) fireList.add(it1)
                        }
                    }

                    readChats(fireList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ACTIVECHATS", error.message)
                }
            })
        }
    }

    private fun readChats(fireList: ArrayList<Chat>) {

        viewModelScope.launch(Dispatchers.IO) {
            val reference = FirebaseDatabase.getInstance().getReference("Users")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val activeChat = mutableSetOf<User>()

                    val children = dataSnapshot.children

                    children.forEach { it1 ->
                        it1.getValue(User::class.java)?.let {
                            for (i in fireList) {
                                if (i.sender == it.id || i.receiver == it.id)
                                    if (it.id != FirebaseAuth.getInstance().currentUser!!.uid)
                                        activeChat.add(it)
                            }
                        }
                    }

                    listChats.value = ArrayList(activeChat)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ACTIVECHATS", error.message)
                }
            })
        }
    }
}