package com.example.moviedb.moviesScreen.detailMoviesSreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.CreditByIdFilmResponse
import com.example.moviedb.modelAPI.MovieByIdResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*
import java.lang.Exception

class DetailMoviesViewModel(private var id: Long, private var context: Context) : ViewModel() {
    private val _detailMovie = MutableLiveData<MovieByIdResponse>()

    val detailMovie: LiveData<MovieByIdResponse>
        get() = _detailMovie

    private val _creditMovie = MutableLiveData<CreditByIdFilmResponse>()

    val castMovie: LiveData<CreditByIdFilmResponse>
        get() = _creditMovie

    private val _save = MutableLiveData<MoviesEntity>()

    val save: LiveData<MoviesEntity>
        get() = _save

    private var _back = MutableLiveData<Boolean>()

    val back : LiveData<Boolean>
        get() = _back

    private var done: Int = 0

    init {
        getDetailMovie()
    }

    private fun getDetailMovie() {
        viewModelScope.launch {
            try{
                var result = API.RETROFIT_SERVICE.getDetailMovieById(id).await()
                _detailMovie.value = result
                getCastMovie()
            }
            catch (e: Exception){
                _detailMovie.value = MovieByIdResponse(false,"",ArrayList(),
                    0,"","","",0F)
            }
        }
    }

    private fun getCastMovie(){
        viewModelScope.launch {
            try{
                var result = API.RETROFIT_SERVICE.getCastMovie(id).await()
                _creditMovie.value = result
                checkSave()
            }
            catch (e: Exception){
                _creditMovie.value = CreditByIdFilmResponse(0,ArrayList())
            }
        }
    }

    private fun checkSave(){
        viewModelScope.launch {
            var value1 = MoviesEntity(false,"","",-1,"","","",0F)
            withContext(Dispatchers.IO){
                value1 = getDatabaseMovie(context).dao.getMovieById(id)
            }
            if (value1==null){
                _save.value =  MoviesEntity(false,"","",-1,"","","",0F)
            }else{
                _save.value = value1
            }
        }
    }


    fun insertMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getDatabaseMovie(context!!).dao.insertMovies(moviesEntity)
                done = 1
            }
            if(done == 0){
                _back.value = true
            }
        }
    }

    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getDatabaseMovie(context!!).dao.deleteMoviesById(moviesEntity.id)
                done = 1
            }
            if (done == 0){
                _back.value = true
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }
}
