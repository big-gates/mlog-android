package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.kychan.mlog.core.model.Genre
import com.kychan.mlog.core.model.MyRatedMovies

data class MyRatedMoviesVO(
    @Embedded val myMovie: MyMovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "my_movie_id"
    )
    val rated: RatedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val tags: List<MyGenresEntity>
)

fun MyRatedMoviesVO.toDomain() = MyRatedMovies(
    myMovieId = myMovie.id,
    adult = myMovie.adult,
    backdropPath = myMovie.backdropPath,
    originalTitle = myMovie.originalTitle,
    posterPath = myMovie.posterPath,
    title = myMovie.title,
    voteAverage = myMovie.voteAverage,
    rank = myMovie.rank,
    rated = rated.rated,
    comment = rated.comment,
    createdAt = rated.createdAt,
    genres = tags.toGenres()
)