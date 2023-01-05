package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProviders

@Entity(
    tableName = "movie"
)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val watchProviders: WatchProviders
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    posterPath = posterPath,
    title = title,
    voteAverage = voteAverage
)
