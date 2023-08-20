package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Genre

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MyMovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["genre_id","movie_id"],
    tableName = "my_genre"
)
data class MyGenresEntity(
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
)

fun List<MyGenresEntity>.toGenres() = this.map { genreEntity ->
    Genre.values().find { genre -> genreEntity.genreId == genre.id }?: throw IllegalArgumentException("genreId is required")
}
