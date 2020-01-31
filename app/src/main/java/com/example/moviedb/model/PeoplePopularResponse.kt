package com.example.moviedb.model

data class PeoplePopularResponse(
    val page: Long,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<Peoples>
)