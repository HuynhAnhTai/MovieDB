package com.example.moviedb.screen.savedScreen.detailSaveScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.repository.MovieRepository
import kotlinx.coroutines.*

class DetailSaveViewModel(application: Application) : AndroidViewModel(application) {
    private var movieRepository = MovieRepository(getApplication())

    private var _movie = MutableLiveData<MoviesEntity>()

    val movie : LiveData<MoviesEntity>
        get() = _movie

    var id: Long = 0
        set(value) {
            field = value
            getDetailFilm()
        }

    private fun getDetailFilm() {
        viewModelScope.launch {
            var value1 = movieRepository.getMoviesByIdFromDB(id)
            if (value1==null){
                _movie.value = MoviesEntity(false,"","",-1,"","","",0F)
            }else{
                _movie.value = value1
            }
        }
    }

    fun insertMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            movieRepository.insertMovieToDB(moviesEntity)
        }
    }

    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            movieRepository.deleteMovieFromDB(moviesEntity.id)
        }
    }

}
