package com.kychan.mlog.core.dataSourceRemote.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchRes(
    val page: Int,
    val results: List<MovieSearch>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieSearch(
    val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String?,
    @SerialName("genre_ids") val genreIds: List<Int>?,
    val id: Int,
    @SerialName("known_for") val knownFor: List<KnownFor>?,
    @SerialName("media_type") val mediaType: String,
    val name: String?,
    @SerialName("origin_country") val originCountry: List<String>?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_name") val originalName: String?,
    @SerialName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?
)

@Serializable
data class KnownFor(
    val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String?,
    @SerialName("genre_ids") val genreIds: List<Int>?,
    val id: Int,
    @SerialName("media_type") val mediaType: String,
    val name: String?,
    @SerialName("origin_country") val originCountry: List<String>?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_name") val originalName: String?,
    @SerialName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?
)