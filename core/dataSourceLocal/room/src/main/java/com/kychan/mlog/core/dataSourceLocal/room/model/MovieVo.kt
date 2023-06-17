package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProvider

data class MovieVo(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val watchProviders: List<WatchProviderEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val tags: List<TagEntity>
)

fun MovieVo.toDomain() = Movie(
    id = movie.id,
    adult = movie.adult,
    backdropPath = movie.backdropPath,
    originalTitle = movie.originalTitle,
    posterPath = movie.posterPath,
    title = movie.title,
    voteAverage = movie.voteAverage,
    watchProvider = watchProviders.map {
        WatchProvider(id = it.watchProviderId, rank = it.rank)
    },
)
