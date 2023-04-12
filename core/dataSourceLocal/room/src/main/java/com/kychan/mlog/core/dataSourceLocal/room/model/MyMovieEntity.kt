package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.WatchProvider

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
    val watchProviders: WatchProvider,
    val rank: Int,
) {
    companion object {
        fun of(myMovie: MyMovie) = MyMovieEntity(
            id = myMovie.id,
            adult = myMovie.adult,
            backdropPath = myMovie.backdropPath,
            originalTitle = myMovie.originalTitle,
            posterPath = myMovie.posterPath,
            title = myMovie.title,
            voteAverage = myMovie.voteAverage,
            watchProviders = myMovie.watchProviders,
            rank = myMovie.rank,
        )
    }
}