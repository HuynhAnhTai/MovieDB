package com.example.moviedb.moviesScreen.detailMoviesSreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DetailMoviesViewModelFactory(private var id: Long): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailMoviesViewModel::class.java)){
            return DetailMoviesViewModel(id) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }

}