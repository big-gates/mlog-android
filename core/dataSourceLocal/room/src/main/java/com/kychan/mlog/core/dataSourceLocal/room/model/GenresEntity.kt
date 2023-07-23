package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Genre

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["genre_id","movie_id"],
    tableName = "genre"
)
data class GenresEntity(
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
)

fun List<GenresEntity>.toGenres() = this.map { genreEntity ->
    Genre.values().filter { genre -> genreEntity.genreId in genre.ids }
}.flatten()
