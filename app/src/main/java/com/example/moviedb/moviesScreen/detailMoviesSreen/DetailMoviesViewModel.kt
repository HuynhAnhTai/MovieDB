package com.example.moviedb.moviesScreen.detailMoviesSreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.CastOfFlim
import com.example.moviedb.model.CreditByIdFilmResponse
import com.example.moviedb.model.MovieByIdResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailMoviesViewModel(private var id: Long) : ViewModel() {
    private val _detailMovie = MutableLiveData<MovieByIdResponse>()

    val detailMovie: LiveData<MovieByIdResponse>
        get() = _detailMovie

    private val _creditMovie = MutableLiveData<CreditByIdFilmResponse>()

    val castMovie: LiveData<CreditByIdFilmResponse>
        get() = _creditMovie

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getDetailMovie()
        getCastMovie()
    }

    private fun getDetailMovie() {
        coroutineScope.launch {
            try{
                var result = API.RETROFIT_SERVICE.getDetailMovieById(id).await()
                _detailMovie.value = result
            }
            catch (e: Exception){
                _detailMovie.value = MovieByIdResponse(false,"",ArrayList(),
                    0,"","","",0F)
            }
        }
    }

    private fun getCastMovie(){
        coroutineScope.launch {
            try{
                var result = API.RETROFIT_SERVICE.getCastMovie(id).await()
                _creditMovie.value = result
            }
            catch (e: Exception){
                _creditMovie.value = CreditByIdFilmResponse(0,ArrayList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
