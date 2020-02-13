package com.example.moviedb.beginScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.moviedb.db.GenresEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.Genres
import com.example.moviedb.modelAPI.GenresReponse
import com.example.moviedb.repository.FilterRepository
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.*

class BeginViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private var filterRepository = FilterRepository(getApplication())

    val type = MutableLiveData<Int>()

    init {
        getGenres()
    }

    fun updateType(type: Int){
        this.type.value = type
    }

    private fun getGenres(){
        viewModelScope.launch {

            lateinit var result: GenresReponse

            result = filterRepository.getGenres()
            var genresEntity = ArrayList<GenresEntity>()

            if(result.genres.size>0){
                for (i in result.genres){
                    var temp = GenresEntity(i.id, i.name)
                    genresEntity.add(temp)
                }
                if (genresEntity.size>0){
                    filterRepository.insertGenresToRoomDB(genresEntity)
                }
            }
        }
    }

}
