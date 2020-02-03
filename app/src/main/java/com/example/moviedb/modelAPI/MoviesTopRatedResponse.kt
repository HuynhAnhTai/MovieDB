package com.example.moviedb.modelAPI

data class MoviesTopRatedResponse(
    val page: Long,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<MoviesTopRatedResults>
)