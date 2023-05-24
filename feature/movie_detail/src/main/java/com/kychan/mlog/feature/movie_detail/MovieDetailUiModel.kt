package com.kychan.mlog.feature.movie_detail

import com.kychan.mlog.core.model.Genre2
import com.kychan.mlog.core.model.MovieDetail

data class MovieDetailUiModel(
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<GenreItemUiModel>,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val status: String,
    val title: String,
)

fun emptyMovieDetailUiModel() = MovieDetailUiModel(
    adult = false,
    backdropPath = "",
    genres = emptyList(),
    overview = "",
    posterPath = "",
    releaseDate = "",
    status = "",
    title = "",
)

data class GenreItemUiModel(
    val id: Int,
    val name: String
)

fun MovieDetail.toUiModel() = MovieDetailUiModel(
    adult = adult,
    backdropPath = backdropPath,
    genres = genres.map(Genre2::toItemUiModel),
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    status = status,
    title = title,
)

fun Genre2.toItemUiModel() = GenreItemUiModel(
    id = id,
    name = name,
)