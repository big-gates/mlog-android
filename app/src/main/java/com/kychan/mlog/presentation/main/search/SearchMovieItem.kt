package com.kychan.mlog.presentation.main.search

data class SearchMovieItem(
    val image: String,
    val title: String,
    val link: String,
    val subTitle: String,
    val director: String,
    val actor: String,
    val pubDate: String,
    val userRating: Float,
    var isMyMovie: Boolean
)
