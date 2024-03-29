package com.kychan.mlog.core.dataSourceRemote.http.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TvSeriesRes(
    val page: Int,
    val results: List<TvSeries>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class TvSeries(
    val id: Int,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val name: String,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)