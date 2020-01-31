package com.example.moviedb.restAPI

import com.example.moviedb.model.MoviesTopRatedResponse
import com.example.moviedb.model.PeoplePopularResponse
import com.example.moviedb.model.SeriesTopRatedResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface APIService {
    @Headers("Content-Type: application/json")
    @GET("movie/top_rated?")
    fun getMoviesTopRated(): Deferred<MoviesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("tv/top_rated?")
    fun getSeriesTopRated(): Deferred<SeriesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("person/popular?")
    fun getPeoplePopular(): Deferred<PeoplePopularResponse>


}