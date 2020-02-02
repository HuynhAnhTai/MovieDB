package com.example.moviedb.savedScreen.detailSaveScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailSaveViewModelFactory(private var context: Context, private var id: Long ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailSaveViewModel::class.java)){
            return DetailSaveViewModel(context, id) as T
        }
        throw IllegalArgumentException ("Unknow View Model")
    }

}