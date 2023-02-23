package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.WatchProviders

@Entity(
    tableName = "my_movie",
    indices = [Index(value = ["id", "watch_providers"], unique = true)],
)
data class MyMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val title: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "watch_providers")
    val watchProviders: WatchProviders,
    val rank: Int,
)