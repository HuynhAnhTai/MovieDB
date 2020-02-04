package com.example.moviedb.filterScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FilterViewModelFactory(private var context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)){
            return FilterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknow viewmodel")
    }
}