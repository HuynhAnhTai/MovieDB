package com.example.moviedb.model

data class Peoples (
    val popularity: Double,
    val known_for_department: String,
    val name: String,
    val id: Long,
    val profile_path: String,
    val adult: Boolean
)