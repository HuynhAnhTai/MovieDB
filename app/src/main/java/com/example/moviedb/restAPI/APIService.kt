package com.example.moviedb.restAPI

import com.example.moviedb.modelAPI.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @Headers("Content-Type: application/json")
    @GET("movie/top_rated?")
    fun getMoviesTopRated(@Query("page") page: Int): Deferred<MoviesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing?")
    fun getMoviesNowPlaying(@Query("page") page: Int): Deferred<MovieNowPlayingResponse>

    @Headers("Content-Type: application/json")
    @GET("tv/top_rated?")
    fun getSeriesTopRated(@Query("page") page: Int): Deferred<SeriesTopRatedResponse>

    @Headers("Content-Type: application/json")
    @GET("person/popular?")
    fun getPeoplePopular(@Query("page") page: Int): Deferred<PeoplePopularResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}?")
    fun getDetailMovieById(@Path ("movie_id") id: Long):Deferred<MovieByIdResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/credits?")
    fun getCastMovie(@Path ("movie_id") id: Long):Deferred<CreditByIdFilmResponse>

    @Headers("Content-Type: application/json")
    @GET("person/{person_id}?")
    fun getInfoPeople(@Path ("person_id") id: Long):Deferred<PersonInfoResponse>

    @Headers("Content-Type: application/json")
    @GET("genre/movie/list?")
    fun getAllGenre():Deferred<GenresReponse>

    @Headers("Content-Type: application/json")
    @GET("search/movie")
    fun getSearchFlim(@Query("query") query: String):Deferred<MoviesTopRatedResponse>
}