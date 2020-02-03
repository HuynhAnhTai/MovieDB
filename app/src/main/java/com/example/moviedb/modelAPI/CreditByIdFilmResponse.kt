package com.example.moviedb.modelAPI

data class CreditByIdFilmResponse (
    val id: Long,
    val cast: ArrayList<CastOfFlim>
)