package com.example.moviedb.screen.loginScreen

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var tv_forgot_pass: TextView
    private lateinit var bt_login: Button
    private lateinit var bt_create_account: Button
    private lateinit var bt_google: SignInButton
    private lateinit var bt_facebook: LoginButton

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private var callbackManagerFB: CallbackManager ?= null
    val GOOGLE: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view= inflater.inflate(R.layout.login_fragment, container, false)

        et_email = view.findViewById(R.id.et_email_login_fragment)
        et_password = view.findViewById(R.id.et_pass_login_fragment)
        tv_forgot_pass = view.findViewById(R.id.tv_forgot_password_login_fragment)
        bt_login = view.findViewById(R.id.bt_login_fragment)
        bt_create_account = view.findViewById(R.id.bt_creat_account_login_fragment)
        bt_google = view.findViewById(R.id.bt_sign_in_google_login_fragment)
        bt_facebook = view.findViewById(R.id.bt_sign_in_facebook_login_fragment)

        callbackManagerFB = CallbackManager.Factory.create()

        bt_facebook.setReadPermissions("email")
        bt_facebook.setFragment(this)

        bt_google.setSize(SignInButton.SIZE_STANDARD)

        if (mAuth.currentUser!=null){
            this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBeginFragment())
        }

        bt_login.setOnClickListener {
            if (checkNetworkAvailable()) {
                checkAccount()
            }else{
                Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show()
            }
        }

        bt_create_account.setOnClickListener {
            this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
        }

        tv_forgot_pass.setOnClickListener {
            this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }

        bt_google.setOnClickListener {
            gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(activity!!,gso)
            signInGoogle()
        }

        callbackManagerFB = CallbackManager.Factory.create()

        bt_facebook.setOnClickListener {
            signInFacebook()
        }

        return view
    }

    private fun signInFacebook() {
        bt_facebook.registerCallback(callbackManagerFB, object :FacebookCallback<LoginResult>{

            override fun onSuccess(result: LoginResult?) {
                viewModel.loginFacebook(FacebookAuthProvider.getCredential(result!!.accessToken.token))
            }

            override fun onCancel() {
                Toast.makeText(context,"Cancel",Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.wrongPass.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (!it){
                    Toast.makeText(context,"Wrong Password",Toast.LENGTH_SHORT).show()
                }
                viewModel.doneSignIn()
            }
        })

        viewModel.verify.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it){
                    this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBeginFragment())

                }else{
                    Toast.makeText(context,"Please verify email or wrong account",Toast.LENGTH_SHORT).show()
                }
                viewModel.doneSignIn()
            }
        })
    }

    private fun checkAccount() {
        var email = et_email.text.toString()
        var pass = et_password.text.toString()

        if (email.equals("") || pass.equals("")){
            Toast.makeText(context,"Please fill enough information",Toast.LENGTH_SHORT).show()
        }else{
            viewModel.login(email,pass)
        }
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManagerFB?.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.loginGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(activity!!,"Failure",Toast.LENGTH_LONG).show()
            }
        }


    }

}
