package com.example.moviedb.screen.loginScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private var _wrongPass = MutableLiveData<Boolean>()

   val wrongPass : LiveData<Boolean>
        get() = _wrongPass

    private var _verify = MutableLiveData<Boolean>()

    val verify : LiveData<Boolean>
        get() = _verify

    fun login(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){
                checkVerify()
            }else{
                _wrongPass.value = false
            }
        }
    }

    private fun checkVerify() {
        if (mAuth.currentUser!!.isEmailVerified){
            _verify.value = true
        }else{
            _verify.value = false
            mAuth.signOut()
        }
    }

    fun doneSignIn(){
        _verify.value = null
        _wrongPass.value = null
    }
}
