package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import com.kychan.mlog.core.model.MyMovieRatedAndWanted

data class MyMovieRatedAndWantedVO(
    val rated: Float?,
    val comment: String?,
    @ColumnInfo("my_movie_id") val wantToMovieId: Int?,
)

fun MyMovieRatedAndWantedVO?.toDomain() = MyMovieRatedAndWanted(
    rated = this?.rated ?: 0f,
    comment = this?.comment.orEmpty(),
    isLike = (this?.wantToMovieId ?: -1) > -1,
)