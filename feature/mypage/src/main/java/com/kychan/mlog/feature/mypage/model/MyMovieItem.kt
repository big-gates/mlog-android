package com.kychan.mlog.feature.mypage.model

import com.kychan.mlog.core.model.Genre
import com.kychan.mlog.core.model.MyRatedMovies
import com.kychan.mlog.core.model.MyWantToWatchMovie

data class MyMovieItem(
    val myMovieId: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val rank: Int,
    val rated: Float,
    val comment: String,
    val createdAt: String,
    val genres: List<Genre>,
) {
    companion object {
        fun of(myRatedMovie: MyRatedMovies, ): MyMovieItem {
            return MyMovieItem(
                myMovieId = myRatedMovie.myMovieId,
                adult = myRatedMovie.adult,
                backdropPath = myRatedMovie.backdropPath,
                originalTitle = myRatedMovie.originalTitle,
                posterPath = myRatedMovie.posterPath,
                title = myRatedMovie.title,
                voteAverage = myRatedMovie.voteAverage,
                rank = myRatedMovie.rank,
                rated = myRatedMovie.rated,
                comment = myRatedMovie.comment,
                createdAt = myRatedMovie.createdAt,
                genres = myRatedMovie.genres,
            )
        }

        fun of(myWTWMovie: MyWantToWatchMovie): MyMovieItem {
            return MyMovieItem(
                myMovieId = myWTWMovie.myMovie.id,
                adult = myWTWMovie.myMovie.adult,
                backdropPath = myWTWMovie.myMovie.backdropPath,
                originalTitle = myWTWMovie.myMovie.originalTitle,
                posterPath = myWTWMovie.myMovie.posterPath,
                title = myWTWMovie.myMovie.title,
                voteAverage = myWTWMovie.myMovie.voteAverage,
                rank = myWTWMovie.myMovie.rank,
                rated = 0.0f,
                comment = "",
                createdAt = myWTWMovie.wantToWatch.createAt,
                genres = myWTWMovie.genres,
            )
        }
    }
}
