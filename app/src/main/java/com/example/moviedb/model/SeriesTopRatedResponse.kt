package com.example.moviedb.model

data class SeriesTopRatedResponse(
    val page: Int,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<SeriesTopRatedResults>
)