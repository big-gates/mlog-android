package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.*
import com.kychan.mlog.core.model.WantToWatch

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
    tableName = "want_to_watches"
)
data class WantToWatchesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "my_movie_id")
    val myMovieId: Int,
    @ColumnInfo("created_at")
    val createdAt: String,
) {
    companion object {
        fun of(wantToWatch: WantToWatch) = WantToWatchesEntity(
            id = wantToWatch.id,
            myMovieId = wantToWatch.myMovieId,
            createdAt = wantToWatch.createAt,
        )
    }
}

fun WantToWatchesEntity.toDomain() = WantToWatch(
    id = this.id,
    myMovieId = this.myMovieId,
)