package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*

@Entity(
    tableName = "watch_provider",
    indices = [Index(value = ["movie_id", "watch_provider_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class WatchProviderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "watch_provider_id") val watchProviderId: Int,
    val rank: Int,
)
