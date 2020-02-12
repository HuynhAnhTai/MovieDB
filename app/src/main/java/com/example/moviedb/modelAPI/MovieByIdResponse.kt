package com.example.moviedb.modelAPI

data class MovieByIdResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: ArrayList<Genres>,
    val id: Long,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Float,
    val videos: Videos
)

data class Videos(
    var results: ArrayList<DetailVideo>
)

data class DetailVideo(
    var id: String,
    var key: String,
    var site: String,
    var name: String
)