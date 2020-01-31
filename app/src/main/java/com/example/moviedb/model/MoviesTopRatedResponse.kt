package com.example.moviedb.model

data class MoviesTopRatedResponse(
    val page: Long,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<Movies>
)