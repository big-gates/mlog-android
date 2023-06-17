package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.MyMovie

@Entity(
    tableName = "my_movie",
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
    val rank: Int,
) {
    fun toDomain() = MyMovie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        posterPath = posterPath,
        title = title,
        voteAverage = voteAverage,
        rank = rank,
    )

    companion object {
        fun of(myMovie: MyMovie) = MyMovieEntity(
            id = myMovie.id,
            adult = myMovie.adult,
            backdropPath = myMovie.backdropPath,
            originalTitle = myMovie.originalTitle,
            posterPath = myMovie.posterPath,
            title = myMovie.title,
            voteAverage = myMovie.voteAverage,
            rank = myMovie.rank,
        )
    }
}