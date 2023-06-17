package com.kychan.mlog.core.data.mapper

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.TagEntity
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.MediaType
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.toMediaType

fun MovieDiscoverRes.genres(): List<List<Int>> = this.results.map { movie ->
    movie.genreIds
}

fun MovieDiscoverRes.toMovieEntity(): List<MovieEntity> = this.results.mapIndexed { index, moviePopular ->
    MovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath?: "",
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
    )
}

fun MoviePopularRes.toMovieEntity(): List<MovieEntity> = this.results.mapIndexed { index, moviePopular ->
    MovieEntity(
        id = moviePopular.id,
        adult = moviePopular.adult,
        backdropPath = moviePopular.backdropPath?: "",
        originalTitle = moviePopular.originalTitle,
        posterPath = moviePopular.posterPath,
        title = moviePopular.title,
        voteAverage = moviePopular.voteAverage,
    )
}

fun MoviePopularRes.genres(): List<List<Int>> = this.results.map { movie ->
    movie.genreIds
}

fun MoviePopularRes.toTagEntity(movieId: Int): List<TagEntity> = this.results.map { movie ->
    movie.genreIds.map { genreId ->
        TagEntity(genreId, movieId)
    }
}.flatten()

internal fun String.toRecentSearchEntity(): RecentSearchEntity =
    RecentSearchEntity(
        text = this,
        createdAt = System.currentTimeMillis().toDateTimeFormat()
    )

fun MovieSearchRes.toModel(
    filters: List<MediaType> = listOf(
        MediaType.MOVIE,
        MediaType.TV,
    )
): List<Movie> = this.results
    .filter { filters.contains(it.mediaType.toMediaType()) }
    .map { movieSearch ->
        Movie(
            id = movieSearch.id,
            adult = movieSearch.adult?: false,
            backdropPath = movieSearch.backdropPath?: "",
            originalTitle = movieSearch.originalTitle?: "",
            posterPath = movieSearch.posterPath?: "",
            title = movieSearch.title?: "",
            voteAverage = movieSearch.voteAverage?: 0.0,
            watchProvider = listOf()
        )
    }