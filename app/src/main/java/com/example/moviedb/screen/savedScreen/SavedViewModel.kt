package com.example.moviedb.screen.savedScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SavedViewModel(application: Application) : AndroidViewModel(application) {

    val movieSave : LiveData<List<MoviesEntity>> = getDatabaseMovie(getApplication()).dao.getMovies()

    override fun onCleared() {
        super.onCleared()
    }
}
