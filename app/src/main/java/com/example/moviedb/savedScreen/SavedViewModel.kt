package com.example.moviedb.savedScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.db.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SavedViewModel(private var context: Context) : ViewModel() {

    var viewModel = Job()
    var conroutineScope = CoroutineScope(viewModel + Dispatchers.Main)


    val movieSave : LiveData<List<MoviesEntity>> = getDatabase(context).moviesDao.getMovies()


    override fun onCleared() {
        super.onCleared()
        viewModel.cancel()
    }
}
