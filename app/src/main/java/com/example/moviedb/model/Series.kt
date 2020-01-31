package com.example.moviedb.model

data class Series(
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