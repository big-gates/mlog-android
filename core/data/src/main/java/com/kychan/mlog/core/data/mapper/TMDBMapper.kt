package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders

private const val DEFAULT_RESULT_COUNT = 20

fun MovieDiscoverRes.toDomain(): List<Movie> = this.results.map {
    Movie(
        id = it.id,
        adult = it.adult,
        backdropPath = it.backdropPath,
        originalTitle = it.originalTitle,
        posterPath = it.posterPath,
        title = it.title,
        voteAverage = it.voteAverage
    )
}

fun MovieDiscoverRes.toEntity(page:Int, watchProviders: WatchProviders): List<MovieEntity> = this.results.mapIndexed { index, moviePopular ->
    MovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath,
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
        watchProviders = watchProviders,
        rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
    )
}

fun MoviePopularRes.toDomain(): List<Movie> = this.results.map {
    Movie(
        id = it.id,
        adult = it.adult,
        backdropPath = it.backdropPath?: "",
        originalTitle = it.originalTitle,
        posterPath = it.posterPath,
        title = it.title,
        voteAverage = it.voteAverage
    )
}

fun MoviePopularRes.toEntity(page:Int, watchProviders: WatchProviders): List<MovieEntity> = this.results.mapIndexed { index, moviePopular ->
    MovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath?: "",
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
        watchProviders = watchProviders,
        rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
    )
}