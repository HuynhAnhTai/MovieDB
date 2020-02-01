package com.example.moviedb.model

data class CreditByIdFilmResponse (
    val id: Long,
    val cast: ArrayList<CastOfFlim>
)