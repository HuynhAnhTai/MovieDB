package com.example.moviedb.screen.savedScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabaseMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SavedViewModel(private var context: Context) : ViewModel() {

    val movieSave : LiveData<List<MoviesEntity>> = getDatabaseMovie(context).dao.getMovies()

    override fun onCleared() {
        super.onCleared()
    }
}
