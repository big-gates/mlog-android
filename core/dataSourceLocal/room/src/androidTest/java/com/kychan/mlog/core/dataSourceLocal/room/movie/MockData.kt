package com.kychan.mlog.core.dataSourceLocal.room.movie

import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity

internal val mockMovieEntities = listOf(
    MovieEntity(
        id = 13,
        adult = false,
        backdropPath = "/qdIMHd4sEfJSckfVJfKQvisL02a.jpg",
        originalTitle = "Forrest Gump",
        posterPath = "/xdJxoq0dtkchOkUz5UVKuxn7a2V.jpg",
        title = "포레스트 검프",
        voteAverage = 8.5
    ),
    MovieEntity(
        id = 98,
        adult = false,
        backdropPath = "/3ZVEtQxVPpEp5LNpAULDcxadTU3.jpg",
        originalTitle = "Gladiator",
        posterPath = "/yemF0xxGU56Pf3JXxVr4C6kuKng.jpg",
        title = "글래디에이터",
        voteAverage = 8.2
    ),
    MovieEntity(
        id = 105,
        adult = false,
        backdropPath = "/3lbTiIN8cVonMUQwaeh5nvn61lr.jpg",
        originalTitle = "Back to the Future",
        posterPath = "/7gGicAJdHRzaUd6qLjH5bSW4Cgi.jpg",
        title = "빽 투 더 퓨쳐",
        voteAverage = 8.3
    )
)

internal val sameMovieIdAndDifferentGenreIdMockTagEntities = listOf(
        GenresEntity(
            genreId = 18,
            movieId = 13,
        ),
        GenresEntity(
            genreId = 28,
            movieId = 13,
        ),
        GenresEntity(
            genreId = 10752,
            movieId = 98,
        ),
        GenresEntity(
            genreId = 28,
            movieId = 105,
        ),
        GenresEntity(
            genreId = 12,
            movieId = 105,
        ),
    )