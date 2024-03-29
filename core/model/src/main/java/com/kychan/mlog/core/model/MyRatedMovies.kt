package com.kychan.mlog.core.model

data class MyRatedMovies(
    val myMovieId: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val rank: Int,
    val rated: Float,
    val comment: String,
    val createdAt: String,
    val genres: List<Genre>
)
