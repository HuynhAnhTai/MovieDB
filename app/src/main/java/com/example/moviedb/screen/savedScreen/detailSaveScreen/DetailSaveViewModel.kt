package com.example.moviedb.screen.savedScreen.detailSaveScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.*

class DetailSaveViewModel(private var context: Context, private var id: Long) : ViewModel() {

    private var _movie = MutableLiveData<MoviesEntity>()

    val movie : LiveData<MoviesEntity>
        get() = _movie

    init {
        getDetailFilm()
    }

    private fun getDetailFilm() {
        viewModelScope.launch {
            var value1 = MoviesEntity(false,"","",-1,"","","",0F)
            withContext(Dispatchers.IO){
                value1 = getDatabaseMovie(context).dao.getMovieById(id)
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
                getDatabaseMovie(context!!).dao.insertMovies(moviesEntity)
            }

        }
    }

    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getDatabaseMovie(context!!).dao.deleteMoviesById(moviesEntity.id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
