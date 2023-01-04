package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Movie

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

fun MoviePopularRes.toDomain(): List<Movie> = this.results.map {
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