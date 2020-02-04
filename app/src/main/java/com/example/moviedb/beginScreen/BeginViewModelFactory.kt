package com.example.moviedb.beginScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BeginViewModelFactory (private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeginViewModel::class.java)){
            return BeginViewModel(context) as T
        }
        throw IllegalArgumentException("Unknow exception")
    }

}