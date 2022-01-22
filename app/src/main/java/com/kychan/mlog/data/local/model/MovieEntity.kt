package com.kychan.mlog.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kychan.mlog.presentation.main.mypage.MyMovieItem
import com.kychan.mlog.presentation.main.search.SearchMovieItem

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "sub_title") val subTitle: String,
    @ColumnInfo(name = "pub_date") val pubDate: String,
    @ColumnInfo(name = "director") val director: String,
    @ColumnInfo(name = "actor") val actor: String,
    @ColumnInfo(name = "user_rating") val userRating: Float,
    @ColumnInfo(name = "evaluation") val evaluation: Float
) {
    fun toMovieItem(): MyMovieItem =
        MyMovieItem(
            link = link,
            title = title,
            image = image,
            subTitle = subTitle,
            pubDate = pubDate,
            director = director,
            actor = actor,
            userRating = userRating,
            evaluation = evaluation,
            isMyMovie = true
        )

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
                userRating = movieItem.userRating,
                evaluation = 0f
            )
        }
    }
}