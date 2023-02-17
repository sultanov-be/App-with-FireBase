package com.example.firebase.ui.main.users

import android.content.ContentValues.TAG
import android.util.Log
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

class UsersViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun getUsers() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")

        viewModelScope.launch(Dispatchers.IO) {
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val fireList = ArrayList<User>()

                    val children = dataSnapshot.children

                    children.forEach {
                        it.getValue(User::class.java)?.let { it1 ->
                            if (firebaseUser?.uid != it1.id) fireList.add(it1)
                        }
                    }

                    listUser.value = fireList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.message)
                }
            })
        }
    }
}