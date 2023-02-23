package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["my_movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id", "my_movie_id"], unique = true)],
    tableName = "rated"
)
data class RatedEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "my_movie_id")
    val myMovieId: Int,
    val rated: Float,
    val comment: Int,
)