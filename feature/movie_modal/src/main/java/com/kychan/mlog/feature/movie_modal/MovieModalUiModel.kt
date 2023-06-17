package com.kychan.mlog.feature.movie_modal

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.model.MyMovie
import com.kychan.mlog.core.model.Rated
import com.kychan.mlog.core.model.WantToWatch
import com.kychan.mlog.core.model.WatchProvider

data class MovieModalUiModel(
    val id: Int = -1,
    val title: String = "",
    val adult: Boolean = false,
    val backgroundImage: String = "",
    val isLike: Boolean = false,
    val comment: String = "",
    val rate: Float = 0f,
    val tags: List<String> = emptyList(),
) {
    fun toMyMovie() = MyMovie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backgroundImage,
        originalTitle = "originalTitle",
        posterPath = this.backgroundImage,
        title = this.title,
        voteAverage = 3.0,
        watchProviders = WatchProvider.None,
        rank = 1,
    )

    fun toWantToWatch() = WantToWatch(
        id = this.id,
        myMovieId = this.id,
        createAt = System.currentTimeMillis().toDateTimeFormat(),
    )

    fun toRated() = Rated(
        id = this.id,
        myMovieId = this.id,
        rated = this.rate,
        comment = this.comment,
    )
}