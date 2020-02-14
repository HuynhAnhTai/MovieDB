package com.example.moviedb.modelFirebase

data class MovieEntityFirebase(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    var genres: String = "",
    var id: Long = 0,
    var overview: String = "",
    var poster_path: String = "",
    var title: String = "",
    var vote_average: Float = 0F
)