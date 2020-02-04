package com.example.moviedb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenresEntity constructor(
    @PrimaryKey
    val id: Long,
    val name: String
)