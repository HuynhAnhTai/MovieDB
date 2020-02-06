package com.example.moviedb.beginScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.GenresEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.Genres
import com.example.moviedb.modelAPI.GenresReponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*

class BeginViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

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
                        getDatabaseMovie(getApplication()).dao.insertGenres(genresEntity)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
