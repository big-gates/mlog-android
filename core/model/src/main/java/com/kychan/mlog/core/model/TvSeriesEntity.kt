package com.kychan.mlog.core.model

data class TvSeriesEntity(
    val id: Int,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val totalPages: Int,
)