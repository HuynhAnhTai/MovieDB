package com.example.moviedb.modelAPI

data class SeriesTopRatedResults(
    val original_name: String,
    val name: String,
    val vote_count: Long,
    val first_air_date: String,
    val backdrop_path: String,
    val original_language: String,
    val id: Long,
    val vote_average: Float,
    val overview: String,
    val poster_path: String
)