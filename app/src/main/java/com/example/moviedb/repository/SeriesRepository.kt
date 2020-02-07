package com.example.moviedb.repository

import com.example.moviedb.modelAPI.SeriesTopRatedResponse
import com.example.moviedb.restAPI.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeriesRepository {

    suspend fun getSeriesTopRate(page: Int): SeriesTopRatedResponse {
        var value = SeriesTopRatedResponse(page,0,0,ArrayList())
        withContext(Dispatchers.IO){
            value = API.RETROFIT_SERVICE.getSeriesTopRated(page).await()
        }
        return value
    }
}