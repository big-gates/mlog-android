package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import java.util.*

@Dao
abstract class MovieDao {
    @Query(
        value = """
            SELECT * FROM mlog_movie AS mm
            ORDER BY rank ASC
        """
    )
    abstract fun getMlogMovie(): PagingSource<Int, MlogMovieEntity>

    @Query(
        value = """
            SELECT * FROM netflix_movie AS nm
            ORDER BY rank ASC
        """
    )
    abstract fun getNetflixMovie(): PagingSource<Int, NetflixMovieEntity>

    @Query(
        value = """
            SELECT * FROM watcha_movie AS wm
            ORDER BY rank ASC
        """
    )
    abstract fun getWatchaMovie(): PagingSource<Int, WatchaMovieEntity>

    @Query("DELETE FROM mlog_movie")
    abstract suspend fun clearMlogMovie()

    @Query("DELETE FROM netflix_movie")
    abstract suspend fun clearNetflixMovie()

    @Query("DELETE FROM watcha_movie")
    abstract suspend fun clearWatchaMovie()

    @Upsert
    abstract suspend fun upsertMlogMovies(entities: List<MlogMovieEntity>)

    @Upsert
    abstract suspend fun upsertNetflixMovies(entities: List<NetflixMovieEntity>)

    @Upsert
    abstract suspend fun upsertWatchaMovies(entities: List<WatchaMovieEntity>)

    @Query(value = """
            SELECT * FROM sync_log
            WHERE type = :syncLogType
        """
    )
    abstract suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity
    @Upsert
    abstract suspend fun upsertSyncLog(syncLogEntity: SyncLogEntity)

    @Transaction
    open suspend fun clearMlogMoviesUpdateSyncLogUpdatedAt() {
        clearMlogMovie()
        val syncLog = getSyncLog(SyncLogType.Mlog_Movie)
        upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }

    @Transaction
    open suspend fun clearNetflixMoviesUpdateSyncLogUpdatedAt() {
        clearNetflixMovie()
        val syncLog = getSyncLog(SyncLogType.Netflix_Movie)
        upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }

    @Transaction
    open suspend fun clearWatchaMoviesUpdateSyncLogUpdatedAt() {
        clearWatchaMovie()
        val syncLog = getSyncLog(SyncLogType.Watcha_Movie)
        upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }
    @Transaction
    open suspend fun updateMlogMoviesAndSyncLogNextKey(
        movieEntities: List<MlogMovieEntity>,
        nextKey: Int,
    ){
        upsertMlogMovies(movieEntities)
        val syncLog = getSyncLog(SyncLogType.Mlog_Movie)
        upsertSyncLog(syncLog.copy(nextKey = nextKey))
    }

    @Transaction
    open suspend fun updateNetflixMoviesAndSyncLogNextKey(
        movieEntities: List<NetflixMovieEntity>,
        nextKey: Int,
    ){
        upsertNetflixMovies(movieEntities)
        val syncLog = getSyncLog(SyncLogType.Netflix_Movie)
        upsertSyncLog(syncLog.copy(nextKey = nextKey))
    }

    @Transaction
    open suspend fun updateWatchaMoviesAndSyncLogNextKey(
        movieEntities: List<WatchaMovieEntity>,
        nextKey: Int,
    ){
        upsertWatchaMovies(movieEntities)
        val syncLog = getSyncLog(SyncLogType.Watcha_Movie)
        upsertSyncLog(syncLog.copy(nextKey = nextKey))
    }

}