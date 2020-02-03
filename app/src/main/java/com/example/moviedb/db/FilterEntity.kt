package com.example.moviedb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilterEntity constructor(
    @PrimaryKey
    val id: Int,
    val sortBy: String,
    val startTime: String,
    val endTime: String,
    val genres: String
)