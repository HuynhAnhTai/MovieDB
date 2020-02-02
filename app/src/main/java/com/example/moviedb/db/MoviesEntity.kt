package com.example.moviedb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesEntity constructor(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: String,
    @PrimaryKey
    val id: Long,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Float
)