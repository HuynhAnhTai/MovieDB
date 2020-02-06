package com.example.moviedb.screen.savedScreen.detailSaveScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.*

class DetailSaveViewModel(application: Application) : AndroidViewModel(application) {

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
            var value1 = MoviesEntity(false,"","",-1,"","","",0F)
            withContext(Dispatchers.IO){
                value1 = getDatabaseMovie(getApplication()).dao.getMovieById(id)
            }
            if (value1==null){
                _movie.value = MoviesEntity(false,"","",-1,"","","",0F)
            }else{
                _movie.value = value1
            }
        }
    }

    fun insertMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getDatabaseMovie(getApplication()).dao.insertMovies(moviesEntity)
            }

        }
    }

    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getDatabaseMovie(getApplication()).dao.deleteMoviesById(moviesEntity.id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
