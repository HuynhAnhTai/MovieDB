package com.example.moviedb.restAPI;

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit


private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(APIWorker.gsonConverter)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(APIWorker.client)
    .baseUrl(BASE_URL)
    .build()

object MoviesAPI {
    val retrofitService: MoviesService by lazy {
        retrofit.create(MoviesService::class.java)
    }
}
