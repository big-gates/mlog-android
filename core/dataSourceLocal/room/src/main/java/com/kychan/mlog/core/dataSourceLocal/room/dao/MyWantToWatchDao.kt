package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class MyWantToWatchDao {

    @Query("SELECT * FROM my_movie AS m INNER JOIN want_to_watches AS w ON m.id = w.my_movie_id")
    abstract fun getMyWantToWatchMovies(): Flow<List<MyWantToWatchMovieVO>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertWantMovie(wantToWatchesEntity: WantToWatchesEntity)

    @Delete
    abstract suspend fun deleteWantMovie(wantToWatchesEntity: WantToWatchesEntity)

    @Query("SELECT * FROM want_to_watches AS w WHERE w.my_movie_id = (:id)")
    abstract suspend fun existToMyWantMovie(id: Int) : WantToWatchesEntity?

}