package com.example.moviedb.modelAPI

data class PeoplePopularResponse(
    val page: Long,
    val total_results: Long,
    val total_pages: Long,
    val results: ArrayList<PeoplesPopularResults>
)