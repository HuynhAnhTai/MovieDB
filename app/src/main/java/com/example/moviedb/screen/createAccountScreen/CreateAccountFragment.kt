package com.example.moviedb.screen.createAccountScreen

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

@Suppress("DEPRECATION")
class CreateAccountFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAccountFragment()
    }

    private lateinit var viewModel: CreateAccountViewModel

    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var et_confrim_password: EditText
    private lateinit var bt_create_account: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.create_account_fragment, container, false)

        et_email = view.findViewById(R.id.et_email_create_account_fragment)
        et_password = view.findViewById(R.id.et_password_create_account_fragment)
        et_confrim_password = view.findViewById(R.id.et_confirm_password_create_account_fragment)
        bt_create_account = view.findViewById(R.id.bt_creat_account_fragment)

        bt_create_account.setOnClickListener {
            if (checkNetworkAvailable()) {
                checkInfo()
            }else{
                Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateAccountViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.successfull.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                if (it) {
                    Toast.makeText(context, "Check your mail", Toast.LENGTH_SHORT).show()
                    this.findNavController()
                        .navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginFragment())
                } else {
                    Toast.makeText(context, "Account existed", Toast.LENGTH_SHORT).show()
                }
                viewModel.doneCreate()
            }
        })
    }



    private fun checkInfo() {
        var email = et_email.text.toString()
        var password = et_password.text.toString()
        var confirm_pass = et_confrim_password.text.toString()

        if (email.equals("") || password.equals("") || confirm_pass.equals("")){
            Toast.makeText(context,"Please fill enough information",Toast.LENGTH_SHORT).show()
        } else if (!email.contains("@gmail.com")){
            Toast.makeText(context,"Email invalid",Toast.LENGTH_SHORT).show()
        }else if(password.length<=6){
            Toast.makeText(context,"Password must more than 6 letters",Toast.LENGTH_SHORT).show()
        }else if(!password.equals(confirm_pass)){
            Toast.makeText(context,"Password and Confirm password is not the same",Toast.LENGTH_SHORT).show()
        }else{
            viewModel.checkAccount(email,password)
        }
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}
