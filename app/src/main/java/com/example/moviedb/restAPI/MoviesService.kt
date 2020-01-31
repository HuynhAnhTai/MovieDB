package com.example.moviedb.restAPI

import com.example.moviedb.model.MoviesTopRatedResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesService {
    @Headers("Content-Type: application/json")
    @GET("top_rated?")
    fun getMoviesTopRated(): Deferred<MoviesTopRatedResponse>
}