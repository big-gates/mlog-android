package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.Rated

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MyMovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["my_movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id", "my_movie_id"], unique = true)],
    tableName = "rated"
)
data class RatedEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "my_movie_id")
    val myMovieId: Int,
    val rated: Float,
    val comment: Int,
) {
    companion object {
        fun of(rated: Rated) = RatedEntity(
            id = rated.id,
            myMovieId = rated.myMovieId,
            rated = rated.rated,
            comment = rated.comment,
        )
    }
}