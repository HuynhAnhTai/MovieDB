package com.example.moviedb.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.db.GenresEntity
import com.example.moviedb.db.getDatabaseMovie
import com.example.moviedb.modelAPI.GenresReponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FilterRepository (private val application: Application){

    val filter_all: LiveData<FilterEntity>
        get() = getDatabaseMovie(application).dao.getFilter()

    suspend fun getGenres(): GenresReponse {
        var value = GenresReponse(ArrayList())
        withContext(Dispatchers.IO){
            try {
                value = API.RETROFIT_SERVICE.getAllGenre().await()
            }
            catch (e: IOException){
                value = GenresReponse(ArrayList())
            }
        }
        return value
    }

    suspend fun insertGenresToRoomDB(genresEntity: ArrayList<GenresEntity>){
        withContext(Dispatchers.IO){
            getDatabaseMovie(application).dao.insertGenres(genresEntity)
        }
    }

    suspend fun getFilterByIdRoomDB(): FilterEntity {
        var value = FilterEntity(0,"", "","","")
        withContext(Dispatchers.IO){
            value = getDatabaseMovie(application).dao.getFilterById(1)
        }
        return value
    }

    suspend fun insertFilterToRoomDB(filter: FilterEntity){
        withContext(Dispatchers.IO){
            getDatabaseMovie(application).dao.insertFilter(filter)
        }
    }
}