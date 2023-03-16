package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Movie

@Entity(
    tableName = "netflix_movie",
    indices = [Index(value = ["id","title"], unique = true)],
)
data class NetflixMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdropPath: String,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("poster_path") val posterPath: String,
    val title: String,
    @ColumnInfo("vote_average") val voteAverage: Double,
    val rank: Int,
)

fun NetflixMovieEntity.toDomain() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    posterPath = posterPath,
    title = title,
    voteAverage = voteAverage,
    rank = rank,
)
