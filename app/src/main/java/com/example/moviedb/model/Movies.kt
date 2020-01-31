package com.example.moviedb.model

data class Movies(
    val popularity: Float,
    val vote_count: Long,
    val video: Boolean,
    val poster_path: String,
    val id: Long,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val title: String,
    val vote_average: Float,
    val overview: String,
    val release_date: String
)