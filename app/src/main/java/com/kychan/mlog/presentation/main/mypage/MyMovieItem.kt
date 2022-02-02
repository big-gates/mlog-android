package com.kychan.mlog.presentation.main.mypage

import java.io.Serializable

data class MyMovieItem(
    val image: String,
    val title: String,
    val link: String,
    val subTitle: String,
    val director: String,
    val actor: String,
    val pubDate: String,
    val userRating: Float,
    val evaluation: Float,
    var isMyMovie: Boolean
) : Serializable
