package com.example.moviedb.moviesScreen

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.MoviesTopRatedResponse
import com.example.moviedb.restAPI.MoviesAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _movies = MutableLiveData<MoviesTopRatedResponse>()

    val movies: LiveData<MoviesTopRatedResponse>
        get() = _movies

    init {
        getMoviesTopRated()
    }

    private fun getMoviesTopRated() {
        coroutineScope.launch {
            try{
                var listResult = MoviesAPI.retrofitService.getMoviesTopRated().await()
                _movies.value = listResult
            }
            catch (e: Exception){
                _movies.value = MoviesTopRatedResponse(0,0,0,ArrayList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
