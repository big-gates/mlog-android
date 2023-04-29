package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.MlogMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.NetflixMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchaMovieEntity
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.MediaType
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.toMediaType

private const val DEFAULT_RESULT_COUNT = 20

fun MovieDiscoverRes.toNetflixMovieEntity(page: Int): List<NetflixMovieEntity> = this.results.mapIndexed { index, moviePopular ->
    NetflixMovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath,
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
        rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
    )
}

fun MovieDiscoverRes.toWatchaMovieEntity(page: Int): List<WatchaMovieEntity> = this.results.mapIndexed { index, moviePopular ->
    WatchaMovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath,
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
        rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
    )
}

fun MoviePopularRes.toMlogMovieEntity(page: Int): List<MlogMovieEntity> = this.results.mapIndexed { index, moviePopular ->
    MlogMovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath?: "",
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
        rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
    )
}

internal fun String.toRecentSearchEntity(): RecentSearchEntity =
    RecentSearchEntity(
        text = this,
        createdAt = System.currentTimeMillis().toDateTimeFormat()
    )

fun MovieSearchRes.toModel(
    page: Int,
    filters: List<MediaType> = listOf(
        MediaType.MOVIE,
        MediaType.TV,
    )
): List<Movie> = this.results
    .filter { filters.contains(it.mediaType.toMediaType()) }
    .mapIndexed { index, movieSearch ->
        Movie(
            id = movieSearch.id,
            adult = movieSearch.adult?: false,
            backdropPath = movieSearch.backdropPath?: "",
            originalTitle = movieSearch.originalTitle?: "",
            posterPath = movieSearch.posterPath?: "",
            title = movieSearch.title?: "",
            voteAverage = movieSearch.voteAverage?: 0.0,
            rank = ((page * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1)
        )
    }