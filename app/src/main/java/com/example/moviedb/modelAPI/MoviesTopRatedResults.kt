package com.example.moviedb.modelAPI

data class MoviesTopRatedResults(
    val popularity: Float,
    val vote_count: Long,
    val video: Boolean,
    val poster_path: String,
    val id: Long,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: ArrayList<Int>,
    val title: String,
    val vote_average: Float,
    val overview: String,
    val release_date: String
)