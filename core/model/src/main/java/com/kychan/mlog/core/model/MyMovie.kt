package com.kychan.mlog.core.model

data class MyMovie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val watchProviders: WatchProvider,
    val rank: Int,
)

data class Rated(
    val id: Int,
    val myMovieId: Int,
    val rated: Float,
    val comment: String,
)

data class WantToWatch(
    val id: Int,
    val myMovieId: Int,
)
