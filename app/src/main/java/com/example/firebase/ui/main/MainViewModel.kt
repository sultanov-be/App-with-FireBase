package com.example.firebase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    val user = MutableLiveData<User?>()

    fun getUserNickname() {
        viewModelScope.launch(Dispatchers.IO) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid
            val reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!)

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

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}