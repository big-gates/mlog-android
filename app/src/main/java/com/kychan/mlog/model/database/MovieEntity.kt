package com.kychan.mlog.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kychan.mlog.presentation.main.search.SearchMovieItem

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "subTitle") val subTitle: String,
    @ColumnInfo(name = "pubDate") val pubDate: String,
    @ColumnInfo(name = "director") val director: String,
    @ColumnInfo(name = "actor") val actor: String,
    @ColumnInfo(name = "userRating") val userRating: Float
) {
    companion object {
        fun of(movieItem: SearchMovieItem): MovieEntity {
            return MovieEntity(
                link = movieItem.link,
                title = movieItem.title,
                image = movieItem.image,
                subTitle = movieItem.subTitle,
                pubDate = movieItem.pubDate,
                director = movieItem.director,
                actor = movieItem.actor,
                userRating = movieItem.userRating
            )
        }
    }
}