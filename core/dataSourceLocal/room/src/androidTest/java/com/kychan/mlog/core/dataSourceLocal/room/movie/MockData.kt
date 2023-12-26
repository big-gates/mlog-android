package com.kychan.mlog.core.dataSourceLocal.room.movie

import com.kychan.mlog.core.dataSourceLocal.room.model.GenresEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.dataSourceLocal.room.model.WatchProviderEntity
import com.kychan.mlog.core.model.WatchProvider

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
    ),
    MovieEntity(
        id = 118,
        adult = false,
        backdropPath = "/bSvUW4JQ6g4QiKvwejcfcPRd4Ke.jpg",
        originalTitle = "Charlie and the Chocolate Factory",
        posterPath = "/b3422i6tIf91RttBRfIz70dBSwn.jpg",
        title = "찰리와 초콜릿 공장",
        voteAverage = 7.0
    ),
    MovieEntity(
        id = 120,
        adult = false,
        backdropPath = "/x2RS3uTcsJJ9IfjNPcgDmukoEcQ.jpg",
        originalTitle = "The Lord of the Rings: The Fellowship of the Ring",
        posterPath = "/7uCvKNKKLRqYYyHIRpphi3yUE6Z.jpg",
        title = "반지의 제왕: 반지 원정대",
        voteAverage = 8.4
    ),
    MovieEntity(
        id = 129,
        adult = false,
        backdropPath = "/mSDsSDwaP3E7dEfUPWy4J0djt4O.jpg",
        originalTitle = "千と千尋の神隠し",
        posterPath = "/rIKWsMItjBLrgt51Xpk4MNAUQe2.jpg",
        title = "센과 치히로의 행방불명",
        voteAverage = 8.5
    ),
    MovieEntity(
        id = 176,
        adult = false,
        backdropPath = "/ok4ot3YbfDYZcINXf91JUfq3maB.jpg",
        originalTitle = "Saw",
        posterPath = "/vRVtn1HLMGMHbUsAJkFLC6oT6pQ.jpg",
        title = "쏘우",
        voteAverage = 7.4
    ),
    MovieEntity(
        id = 215,
        adult = false,
        backdropPath = "/1lWeIA03iGXTlDv0yzsN0wUdT9T.jpg",
        originalTitle = "Saw II",
        posterPath = "/cxGoZv6pYcDEN1nMBtOYEhSDdx4.jpg",
        title = "쏘우 2",
        voteAverage = 6.6
    ),
    MovieEntity(
        id = 238,
        adult = false,
        backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
        originalTitle = "The Godfather",
        posterPath = "/I1fkNd5CeJGv56mhrTDoOeMc2r.jpg",
        title = "대부",
        voteAverage = 8.7
    ),
    MovieEntity(
        id = 240,
        adult = false,
        backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
        originalTitle = "The Godfather Part II",
        posterPath = "/bhqvqYuAgrTGwyNAmMR0ZVmjXel.jpg",
        title = "대부 2",
        voteAverage = 8.6
    ),
    MovieEntity(
        id = 872585,
        adult = false,
        backdropPath = "/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
        originalTitle = "Oppenheimer",
        posterPath = "/4ZLnVUfiCe3wX8Ut9eyujndpyvA.jpg",
        title = "오펜하이머",
        voteAverage = 8.2
    ),
    MovieEntity(
        id = 507089,
        adult = false,
        backdropPath = "/t5zCBSB5xMDKcDqe91qahCOUYVV.jpg",
        originalTitle = "Five Nights at Freddy's",
        posterPath = "/h3hhfWdBhdb2JLMZprQ1IvBe90h.jpg",
        title = "프레디의 피자가게",
        voteAverage = 7.9
    ),
    MovieEntity(
        id = 670292,
        adult = false,
        backdropPath = "/1AZcHRuWvmuUNhLj3XWcd54V80B.jpg",
        originalTitle = "The Creator",
        posterPath = "/vFsSluuzqxR46Ils9ib52ItdE9u.jpg",
        title = "크리에이터",
        voteAverage = 7.2
    ),
)


internal val mockWatchProviderEntities = listOf(
    WatchProviderEntity(
        watchProviderId = WatchProvider.NETFLIX_ID,
        movieId = 13,
        rank = 51
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.WATCHA_ID,
        movieId = 13,
        rank = 25
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.WATCHA_ID,
        movieId = 98,
        rank = 41
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.NETFLIX_ID,
        movieId = 105,
        rank = 52
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.NETFLIX_ID,
        movieId = 118,
        rank = 30
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.NETFLIX_ID,
        movieId = 120,
        rank = 15
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.WATCHA_ID,
        movieId = 129,
        rank = 44
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.NETFLIX_ID,
        movieId = 215,
        rank = 44
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.WATCHA_ID,
        movieId = 238,
        rank = 14
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.WATCHA_ID,
        movieId = 240,
        rank = 66
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.MLOG_ID,
        movieId = 872585,
        rank = 1
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.MLOG_ID,
        movieId = 507089,
        rank = 2
    ),
    WatchProviderEntity(
        watchProviderId = WatchProvider.MLOG_ID,
        movieId = 670292,
        rank = 3
    ),
)

internal val mockGenreEntities = listOf(
    GenresEntity(
        genreId = 35,
        movieId = 13
    ),
    GenresEntity(
        genreId = 18,
        movieId = 13
    ),
    GenresEntity(
        genreId = 10749,
        movieId = 13
    ),
    GenresEntity(
        genreId = 28,
        movieId = 98
    ),
    GenresEntity(
        genreId = 18,
        movieId = 98
    ),
    GenresEntity(
        genreId = 12,
        movieId = 98
    ),
    GenresEntity(
        genreId = 12,
        movieId = 105
    ),
    GenresEntity(
        genreId = 35,
        movieId = 105
    ),
    GenresEntity(
        genreId = 878,
        movieId = 105
    ),
    GenresEntity(
        genreId = 12,
        movieId = 118
    ),
    GenresEntity(
        genreId = 35,
        movieId = 118
    ),
    GenresEntity(
        genreId = 10751,
        movieId = 118
    ),
    GenresEntity(
        genreId = 14,
        movieId = 118
    ),
    GenresEntity(
        genreId = 12,
        movieId = 120
    ),
    GenresEntity(
        genreId = 14,
        movieId = 120
    ),
    GenresEntity(
        genreId = 28,
        movieId = 120
    ),
    GenresEntity(
        genreId = 16,
        movieId = 129
    ),
    GenresEntity(
        genreId = 10751,
        movieId = 129
    ),
    GenresEntity(
        genreId = 14,
        movieId = 129
    ),
    GenresEntity(
        genreId = 27,
        movieId = 215
    ),
    GenresEntity(
        genreId = 18,
        movieId = 872585
    ),
    GenresEntity(
        genreId = 36,
        movieId = 872585
    ),
    GenresEntity(
        genreId = 27,
        movieId = 507089
    ),
    GenresEntity(
        genreId = 9648,
        movieId = 507089
    ),
    GenresEntity(
        genreId = 878,
        movieId = 670292
    ),
    GenresEntity(
        genreId = 28,
        movieId = 670292
    ),
    GenresEntity(
        genreId = 53,
        movieId = 670292
    ),
)

internal val mockSyncLogEntities = listOf(
    SyncLogEntity(
        id = 1,
        type = SyncLogType.Netflix_Movie,
        nextKey = 5,
        createdAt = "2023-11-10",
        updatedAt = "2023-11-18"
    ),
    SyncLogEntity(
        id = 2,
        type = SyncLogType.Watcha_Movie,
        nextKey = 5,
        createdAt = "2023-11-10",
        updatedAt = "2023-11-18"
    ),
    SyncLogEntity(
        id = 3,
        type = SyncLogType.Mlog_Movie,
        nextKey = 5,
        createdAt = "2023-11-10",
        updatedAt = "2023-11-18"
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