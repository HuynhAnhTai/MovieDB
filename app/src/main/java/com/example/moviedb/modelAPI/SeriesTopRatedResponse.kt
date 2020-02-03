package com.example.moviedb.modelAPI

data class SeriesTopRatedResponse(
    val page: Int,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<SeriesTopRatedResults>
)