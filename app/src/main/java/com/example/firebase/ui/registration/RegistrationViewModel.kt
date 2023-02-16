package com.example.firebase.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    val isRegistered: MutableLiveData<Boolean> = MutableLiveData()
    fun register(userName: String, email: String, password: String) {
        val auth = FirebaseAuth.getInstance()

        viewModelScope.launch(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser!!.uid

                    val reference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["id"] = userId
                    hashMap["username"] = userName
                    hashMap["imageUrl"] = "default"

                    reference.setValue(hashMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            isRegistered.value = true
                        } else if (it.isCanceled) {
                            isRegistered.value = false
                        }
                    }
                }
            }
        }
    }
}