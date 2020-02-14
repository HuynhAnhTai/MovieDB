package com.example.moviedb.screen.createAccountScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountViewModel : ViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _successfull = MutableLiveData<Boolean>()

    val successfull: LiveData<Boolean>
        get() = _successfull

    fun checkAccount(email: String, password: String) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            if (it.result!!.signInMethods!!.size == 0){
                createAccount(email, password)
            }else{
                _successfull.value = false
            }
        }

    }

    private fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                sendEmailVerification()
            }else{
                _successfull.value = false
            }
        }
    }

    private fun sendEmailVerification() {
        mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
            mAuth.signOut()
            _successfull.value = it.isSuccessful
        }
    }


    fun doneCreate(){
        _successfull.value = null
    }
}
