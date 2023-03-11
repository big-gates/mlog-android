package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MyMovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id", "movie_id"], unique = true)],
    tableName = "tags"
)
data class TagsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    val name: String,
)
