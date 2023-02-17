package com.example.firebase.ui.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {
    val user = MutableLiveData<User?>()

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

    fun sendMessage(msg: String, to: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fUser = FirebaseAuth.getInstance().currentUser!!.uid

            val reference = FirebaseDatabase.getInstance().reference

            val message = HashMap<String, Any>()
            message["sender"] = fUser
            message["receiver"] = to
            message["message"] = msg

            reference.child("Chats").push().setValue(message)
        }
    }
}