package com.example.moviedb.moviesScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MoviesViewModelFactory (private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            return MoviesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknow viewmodel")
    }

}