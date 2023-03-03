package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProvider

@Entity(
    tableName = "movie",
    indices = [Index(value = ["id","watch_provider_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = WatchProviderEntity::class,
            parentColumns = ["id"],
            childColumns = ["watch_provider_id"],
        )
    ]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdropPath: String,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("poster_path") val posterPath: String,
    val title: String,
    @ColumnInfo("vote_average") val voteAverage: Double,
    @ColumnInfo("watch_provider_id") val watchProviderId: Int,
    val rank: Int,
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
