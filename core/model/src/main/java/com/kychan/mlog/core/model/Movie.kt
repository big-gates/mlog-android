package com.kychan.mlog.core.model

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val rank: Int,
)
