package com.example.firebase.ui.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    val isLogged: MutableLiveData<Boolean> = MutableLiveData()
    fun signIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()

        viewModelScope.launch(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogged.value = true
                    } else if (it.isCanceled)
                        isLogged.value = false
                }
        }
    }
}