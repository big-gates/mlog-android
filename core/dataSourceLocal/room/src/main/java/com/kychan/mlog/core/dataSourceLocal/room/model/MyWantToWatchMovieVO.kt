package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.kychan.mlog.core.model.MyWantToWatchMovie

data class MyWantToWatchMovieVO(
    @Embedded val myMovie: MyMovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "my_movie_id"
    )
    val wantToWatch: WantToWatchesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val tags: List<MyGenresEntity>
)

fun MyWantToWatchMovieVO.toDomain() = MyWantToWatchMovie(
    myMovie = myMovie.toDomain(),
    wantToWatch = wantToWatch.toDomain(),
    genres = tags.toGenres()
)