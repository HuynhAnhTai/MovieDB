package com.example.moviedb.moviesScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.repository.FilterRepository
import com.example.moviedb.repository.MovieRepository
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private var filterRepository = FilterRepository(getApplication())
    private var moviesRepository = MovieRepository(getApplication())

    private val _movies = MutableLiveData<List<MoviesTopRatedResults>>()

    val movies: LiveData<List<MoviesTopRatedResults>>
        get() = _movies

    val filter_all: LiveData<FilterEntity>
        get() = filterRepository.filter_all

    private var filter: FilterEntity = FilterEntity(0,"","now","now","")

    var moviePage : Int =0
    var movieTheaterPage : Int =0

    fun getFilter() {
        viewModelScope.launch {
            filter = filterRepository.getFilterByIdRoomDB()
            if (filter == null || filter.startTime == "now"){
                getMoviesTheatre()
            }else{
                getMoviesBetweenDate()
            }
        }
    }

    private fun getMoviesBetweenDate() {
        viewModelScope.launch {
            try{
                moviePage++
                var listResult = moviesRepository
                    .getMovieBetweenDate(filter.sortBy,moviePage,filter.startTime,
                    filter.endTime,
                    filter.genres)

                _movies.value = listResult.results
            }
            catch (e: Exception){
                _movies.value = ArrayList()
                moviePage--
            }
        }
    }

    private fun getMoviesTheatre() {
        viewModelScope.launch {
            try{
                movieTheaterPage++
                if (filter==null){
                    var listResult = moviesRepository.getMovieTheater("",movieTheaterPage,"")
                    _movies.value = listResult.results
                }else {
                    var listResult = moviesRepository.getMovieTheater(
                        filter.sortBy,
                        movieTheaterPage,
                        filter.genres
                    )
                    _movies.value = listResult.results
                }
            }
            catch (e: Exception){
                _movies.value = ArrayList()
                movieTheaterPage--
            }
        }
    }

    fun done(){
        _movies.value = ArrayList()
    }


}
