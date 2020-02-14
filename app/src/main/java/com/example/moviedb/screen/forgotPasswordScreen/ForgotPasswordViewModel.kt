package com.example.moviedb.screen.forgotPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _successfull = MutableLiveData<Boolean>()

    val successfull: LiveData<Boolean>
        get() = _successfull

    fun checkMail(email: String) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            if (it.result!!.signInMethods!!.size == 0){
                _successfull.value = false
            }else{
                sendRequest(email)
            }
        }
    }

    private fun sendRequest(email: String) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            _successfull.value = it.isSuccessful
        }
    }

    fun done(){
        _successfull.value = null
    }
}
