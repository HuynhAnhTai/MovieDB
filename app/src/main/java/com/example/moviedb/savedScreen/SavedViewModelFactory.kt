package com.example.moviedb.savedScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SavedViewModelFactory(private var context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)){
            return SavedViewModel(context) as T
        }
        throw IllegalArgumentException ("Unknow View Model")
    }
}