package com.example.moviedb.restAPI

import com.example.moviedb.modelAPI.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("movie/top_rated?")
    fun getMoviesTopRated(@Query("page") page: Int): Deferred<MoviesTopRatedResponse>

    @GET("movie/now_playing?")
    fun getMoviesNowPlaying(@Query("page") page: Int): Deferred<MovieNowPlayingResponse>

    @GET("tv/top_rated?")
    fun getSeriesTopRated(@Query("page") page: Int): Deferred<SeriesTopRatedResponse>

    @GET("person/popular?")
    fun getPeoplePopular(@Query("page") page: Int): Deferred<PeoplePopularResponse>

    @GET("movie/{movie_id}?")
    fun getDetailMovieById(@Path ("movie_id") id: Long):Deferred<MovieByIdResponse>

    @GET("movie/{movie_id}/credits?")
    fun getCastMovie(@Path ("movie_id") id: Long):Deferred<CreditByIdFilmResponse>

    @GET("person/{person_id}?")
    fun getInfoPeople(@Path ("person_id") id: Long):Deferred<PersonInfoResponse>

    @GET("genre/movie/list?")
    fun getAllGenre():Deferred<GenresReponse>

    @GET("search/movie")
    fun getSearchFlim(@Query("query") query: String, @Query("page") page:Int):Deferred<MoviesTopRatedResponse>

    @GET("discover/movie?")
    fun getFilterFlimBetweenDates(@Query("sort_by") sortBy: String,
                                  @Query("page") page:Int,
                                  @Query("release_date.gte") from: String,
                                  @Query("release_date.lte") to: String,
                                  @Query("with_genres") genres: String)
            :Deferred<MoviesTopRatedResponse>

    @GET("discover/movie?with_release_type=1")
    fun getFilterFlimTheatre(@Query("sort_by") sortBy: String,
                             @Query("page") page:Int,
                             @Query("with_genres") genres: String)
            :Deferred<MoviesTopRatedResponse>
}