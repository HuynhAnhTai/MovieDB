package com.example.moviedb.restAPI

import com.example.moviedb.modelAPI.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface APIService {
    @Headers("Content-Type: application/json")
    @GET("movie/top_rated?")
    fun getMoviesTopRated(): Deferred<MoviesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing?")
    fun getMoviesNowPlaying(): Deferred<MovieNowPlayingResponse>

    @Headers("Content-Type: application/json")
    @GET("tv/top_rated?")
    fun getSeriesTopRated(): Deferred<SeriesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("person/popular?")
    fun getPeoplePopular(): Deferred<PeoplePopularResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}?")
    fun getDetailMovieById(@Path ("movie_id") id: Long):Deferred<MovieByIdResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/credits?")
    fun getCastMovie(@Path ("movie_id") id: Long):Deferred<CreditByIdFilmResponse>

    @Headers("Content-Type: application/json")
    @GET("person/{person_id}?")
    fun getInfoPeople(@Path ("person_id") id: Long):Deferred<PersonInfoResponse>


}