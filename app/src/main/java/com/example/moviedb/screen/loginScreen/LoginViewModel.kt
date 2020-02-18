package com.example.moviedb.screen.loginScreen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application) : AndroidViewModel(application) {

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

    fun loginGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _verify.value = true
            }
        }
    }

    fun loginFacebook(credential: AuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _verify.value = true
                }
            }
    }


    fun doneSignIn(){
        _verify.value = null
        _wrongPass.value = null
    }




}
