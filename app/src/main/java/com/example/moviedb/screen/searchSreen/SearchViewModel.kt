package com.example.moviedb.screen.searchSreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.modelAPI.MovieNowPlayingResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _movies = MutableLiveData<List<MoviesTopRatedResults>>()

    val movies: LiveData<List<MoviesTopRatedResults>>
        get() = _movies

    fun getMoviesSearch(movie: String){
        viewModelScope.launch {
            var value = MoviesTopRatedResponse(0,0,0,ArrayList())
            withContext(Dispatchers.IO){
                value = API.RETROFIT_SERVICE.getSearchFlim(movie).await()
            }
            if(value.results.size>0) {
                _movies.value = value.results
            }
        }
    }
}
