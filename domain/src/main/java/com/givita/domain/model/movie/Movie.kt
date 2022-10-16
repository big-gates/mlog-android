package com.givita.domain.model.movie

data class Movie(
    val id: Int,
    val title: String,
    val link: String,
    val image: String,
    val subTitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float
)
