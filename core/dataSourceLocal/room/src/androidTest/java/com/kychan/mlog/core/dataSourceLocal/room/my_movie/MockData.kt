package com.kychan.mlog.core.dataSourceLocal.room.my_movie

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.MyMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.RatedEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.WantToWatchesEntity

internal val myMovieList = listOf(
    MyMovieEntity(
        id = 961268,
        adult = true,
        backdropPath = "",
        originalTitle = "a",
        posterPath = "a",
        title = "a",
        voteAverage = 0.0,
        rank = 1,
    ),
    MyMovieEntity(
        id = 1151534,
        adult = false,
        backdropPath = "",
        originalTitle = "b",
        posterPath = "b",
        title = "b",
        voteAverage = 1.0,
        rank = 2,
    ),
    MyMovieEntity(
        id = 961269,
        adult = false,
        backdropPath = "",
        originalTitle = "b",
        posterPath = "b",
        title = "b",
        voteAverage = 1.0,
        rank = 3,
    ),
)

internal val myRatedList = listOf(
    RatedEntity(
        id = 961268,
        myMovieId = 961268,
        rated = 4.0f,
        comment = "",
    ),
    RatedEntity(
        id = 1151534,
        myMovieId = 1151534,
        rated = 3.5f,
        comment = "",
    )
)

internal val myWantToWatchList = listOf(
    WantToWatchesEntity(
        id = 961268,
        myMovieId = 961268,
        createdAt = System.currentTimeMillis().toDateTimeFormat(),
    ),
    WantToWatchesEntity(
        id = 1151534,
        myMovieId = 1151534,
        createdAt = System.currentTimeMillis().toDateTimeFormat(),
    ),
)