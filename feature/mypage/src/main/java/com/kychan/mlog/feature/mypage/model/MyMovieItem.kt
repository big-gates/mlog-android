package com.kychan.mlog.feature.mypage.model

import com.kychan.mlog.core.model.MyRatedMovies
import com.kychan.mlog.core.model.WatchProvider

data class MyMovieItem(
    val myMovieId: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val watchProviders: WatchProvider,
    val rank: Int,
    val rated: Float,
    val comment: String,
) {
    companion object {
        fun of(myRatedMovie: MyRatedMovies): MyMovieItem {
            return MyMovieItem(
                myMovieId = myRatedMovie.myMovieId,
                adult = myRatedMovie.adult,
                backdropPath = myRatedMovie.backdropPath,
                originalTitle = myRatedMovie.originalTitle,
                posterPath = myRatedMovie.posterPath,
                title = myRatedMovie.title,
                voteAverage = myRatedMovie.voteAverage,
                watchProviders = myRatedMovie.watchProviders,
                rank = myRatedMovie.rank,
                rated = myRatedMovie.rated,
                comment = myRatedMovie.comment,
            )
        }
    }
}
