package com.example.spotifystats.domain.model

data class Availability(
    val name: String = "",
    val image: String = "",
    val countries: List<Country> = emptyList()
) {
    data class Country(
        val name: String,
        val flag: Int
    )
}
