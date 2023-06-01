package com.kychan.mlog.feature.movie_detail

import com.kychan.mlog.core.model.Genre2
import com.kychan.mlog.core.model.MovieDetail
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.WatchProvider

data class MovieDetailUiModel(
    val id: Int,
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
    id = -1,
    adult = false,
    backdropPath = "",
    genres = emptyList(),
    overview = "",
    posterPath = "",
    releaseDate = "",
    status = "",
    title = "",
)
fun MovieDetailUiModel.toMyMovie() = MyMovie(
    id = this.id,
    adult = this.adult,
    backdropPath = this.backdropPath.orEmpty(),
    originalTitle = "",
    posterPath = this.posterPath.orEmpty(),
    title = this.title,
    voteAverage = 0.0,
    watchProviders = WatchProvider.None,
    rank = 0,
)

data class GenreItemUiModel(
    val id: Int,
    val name: String
)

fun MovieDetail.toUiModel() = MovieDetailUiModel(
    id = id,
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