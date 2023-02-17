package com.example.firebase.ui.message

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.firebase.Chat
import com.example.firebase.firebase.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {
    val user = MutableLiveData<User?>()
    val listChat = MutableLiveData<ArrayList<Chat>>()

    fun getData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(id)

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user1: User? = dataSnapshot.getValue(User::class.java)

                    if (user1 != null) {
                        user.value = user1
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun sendMessage(msg: String, from: String, to: String) {
        viewModelScope.launch(Dispatchers.IO) {


            val reference = FirebaseDatabase.getInstance().reference

            val message = HashMap<String, Any>()
            message["sender"] = from
            message["receiver"] = to
            message["message"] = msg

            reference.child("Chats").push().setValue(message)
        }
    }

    fun getMessages(myId: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val reference = FirebaseDatabase.getInstance().getReference("Chats")

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = ArrayList<Chat>()

                    val children = snapshot.children

                    children.forEach { message ->
                        message.getValue(Chat::class.java)?.let {
                            if ((it.sender == myId && it.receiver == userId)
                                || (it.receiver == myId && it.sender == userId)) {
                                   chatList.add(it)
                            }
                        }
                    }

                    listChat.value = chatList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(ContentValues.TAG, error.message)
                }
            })
        }
    }
}