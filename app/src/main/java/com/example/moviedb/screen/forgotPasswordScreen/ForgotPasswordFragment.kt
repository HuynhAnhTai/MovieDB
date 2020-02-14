package com.example.moviedb.screen.forgotPasswordScreen

import android.content.Context
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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.moviedb.R

class ForgotPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: ForgotPasswordViewModel

    private lateinit var et_pass: EditText
    private lateinit var bt_reset: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.forgot_password_fragment, container, false)

        et_pass = view.findViewById(R.id.et_email_forgot_password_fragment)
        bt_reset = view.findViewById(R.id.bt_reset_password_forgot_password_fragment)

        bt_reset.setOnClickListener {
            if (checkNetworkAvailable()){
                requestResetPassword()
            }else{
                Toast.makeText(context,"No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.successfull.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it){
                    Toast.makeText(context,"Check Mail",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Account not exists",Toast.LENGTH_SHORT).show()
                }
                viewModel.done()
            }
        })
    }

    private fun requestResetPassword() {
        var email = et_pass.text.toString()
        viewModel.checkMail(email)
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}
