package com.example.moviedb.model

data class MovieByIdResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: ArrayList<Genres>,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Float
)