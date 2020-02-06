package com.example.moviedb.moviesScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.MoviesTopRatedResponse
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<MoviesTopRatedResults>>()

    val movies: LiveData<List<MoviesTopRatedResults>>
        get() = _movies

    val filter_all: LiveData<FilterEntity>
        get() = getDatabaseMovie(getApplication()).dao.getFilter()

    private var filter: FilterEntity = FilterEntity(0,"","now","now","")

    var moviePage : Int =0
    var movieTheaterPage : Int =0

    fun getFilter() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                filter = getFilterData()
                if (filter == null || filter.startTime == "now"){
                    getMoviesTheatre()
                }else{
                    getMoviesBetweenDate()
                }
            }
        }
    }

    private suspend fun getFilterData(): FilterEntity {
        return getDatabaseMovie(getApplication()).dao.getFilterById(1)
    }

    private fun getMoviesBetweenDate() {
        viewModelScope.launch {
            try{
                moviePage++
                var listResult = MoviesTopRatedResponse(0,0,0,ArrayList())
                if (filter.sortBy.equals("title") || filter.sortBy.equals("")){
                    listResult = API.RETROFIT_SERVICE
                        .getFilterFlimBetweenDates("original_title.asc",
                            moviePage, filter.startTime,
                            filter.endTime,
                            filter.genres)
                        .await()
                }else {
                    listResult = API.RETROFIT_SERVICE
                        .getFilterFlimBetweenDates(filter.sortBy+".desc",
                            moviePage, filter.startTime,
                            filter.endTime,
                            filter.genres)
                        .await()
                }
                _movies.value = listResult.results
            }
            catch (e: Exception){
                _movies.value = ArrayList()
            }
        }
    }

    private fun getMoviesTheatre() {
        viewModelScope.launch {
            try{
                movieTheaterPage++
                var listResult = MoviesTopRatedResponse(0,0,0,ArrayList())
                if (filter.sortBy.equals("title") || filter.sortBy.equals("")){
                    listResult = API.RETROFIT_SERVICE
                        .getFilterFlimTheatre("original_title.asc",
                            movieTheaterPage,
                            filter.genres)
                        .await()
                }else {
                    listResult = API.RETROFIT_SERVICE
                        .getFilterFlimTheatre(
                            filter.sortBy + ".desc",
                            movieTheaterPage,
                            filter.genres
                        ).await()
                }
                _movies.value = listResult.results
            }
            catch (e: Exception){
                _movies.value = ArrayList()
            }
        }
    }

    fun done(){
        _movies.value = ArrayList()
    }


    override fun onCleared() {
        super.onCleared()
    }
}
