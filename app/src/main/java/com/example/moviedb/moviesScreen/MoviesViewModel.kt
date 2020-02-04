package com.example.moviedb.moviesScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesViewModel(private var context: Context) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _movies = MutableLiveData<List<MoviesTopRatedResults>>()

    val movies: LiveData<List<MoviesTopRatedResults>>
        get() = _movies

    val filter_all: LiveData<FilterEntity>
        get() = getDatabaseMovie(context).dao.getFilter()

    private var filter: FilterEntity = FilterEntity(0,"","","","")

    var moviePage : Int =0
    var movieTheaterPage : Int =0
    init {
        getFilter()
    }

    fun getFilter() {
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                filter = getDatabaseMovie(context).dao.getFilterById(1)
            }
            if (filter == null || filter.startTime == ""){
                movieTheaterPage++
                getMoviesTheatre()
            }else{
                moviePage++
                getMoviesTopRated()
            }
        }
    }

    private fun getMoviesTopRated() {
        coroutineScope.launch {
            try{
                var listResult = API.RETROFIT_SERVICE.getMoviesTopRated(moviePage).await()
                _movies.value = listResult.results
            }
            catch (e: Exception){
                _movies.value = ArrayList()
            }
        }
    }

    private fun getMoviesTheatre() {
        coroutineScope.launch {
            try{
                var listResult = API.RETROFIT_SERVICE.getMoviesNowPlaying(movieTheaterPage).await()
                _movies.value = listResult.results
            }
            catch (e: Exception){
                _movies.value = ArrayList()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
