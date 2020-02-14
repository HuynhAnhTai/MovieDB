package com.example.moviedb.screen.savedScreen.detailSaveScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.repository.MovieRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*

class DetailSaveViewModel(application: Application) : AndroidViewModel(application) {
    private var movieRepository = MovieRepository(getApplication())

    private var movieRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private var _movie = MutableLiveData<MoviesEntity>()

    private var _back = MutableLiveData<Boolean>()

    val back : LiveData<Boolean>
        get() = _back

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

    fun deleteMovie(moviesEntity: MoviesEntity){
        viewModelScope.launch {
            movieRef.child("SaveMovie").child(mAuth.currentUser!!.uid)
                .child(moviesEntity.id.toString()).removeValue()
            movieRepository.deleteMovieFromDB(moviesEntity.id)
            _back.value = true
        }
    }

}
