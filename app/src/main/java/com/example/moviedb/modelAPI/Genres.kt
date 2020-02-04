package com.example.moviedb.modelAPI

data class GenresReponse(
    val genres: ArrayList<Genres>
)

data class Genres (
    val id: Long,
    val name: String
)