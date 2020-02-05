package com.example.moviedb.beginScreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.db.GenresEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.Genres
import com.example.moviedb.modelAPI.GenresReponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*

class BeginViewModel(private val context: Context) : ViewModel() {
    // TODO: Implement the ViewModel

    val genresDB = getDatabaseMovie(context).dao.getGenres()

    init {
        getGenres()
    }

    private fun getGenres(){
        viewModelScope.launch {

            lateinit var result: GenresReponse
            withContext(Dispatchers.IO){
                result = API.RETROFIT_SERVICE.getAllGenre().await()
                var genresEntity = ArrayList<GenresEntity>()

                if(result.genres.size>0){
                    for (i in result.genres){
                        var temp = GenresEntity(i.id, i.name)
                        genresEntity.add(temp)
                    }
                    if (genresEntity.size>0){
                        getDatabaseMovie(context).dao.insertGenres(genresEntity)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
