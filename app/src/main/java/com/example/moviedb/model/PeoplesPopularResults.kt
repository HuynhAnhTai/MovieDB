package com.example.moviedb.model

data class PeoplesPopularResults (
    val popularity: Double,
    val known_for_department: String,
    val name: String,
    val id: Long,
    val profile_path: String,
    val adult: Boolean
)