package com.example.moviedb.model

data class PersonInfoResponse (
    val id: Long,
    val birthday: String,
    val name: String,
    val biography: String,
    val place_of_birth: String,
    val profile_path: String
)