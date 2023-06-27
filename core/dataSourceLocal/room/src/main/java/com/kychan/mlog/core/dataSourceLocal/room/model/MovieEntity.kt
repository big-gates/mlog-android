package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*

@Entity(
    tableName = "movie",
)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdropPath: String,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("poster_path") val posterPath: String,
    val title: String,
    @ColumnInfo("vote_average") val voteAverage: Double,
)

