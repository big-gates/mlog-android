package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import androidx.room.*
import com.kychan.mlog.core.dataSourceLocal.room.model.MyRatedMoviesVO
import kotlinx.coroutines.flow.Flow

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

    @Upsert
    abstract fun upsertMlogMovies(entities: List<MlogMovieEntity>)

    @Upsert
    abstract fun upsertNetflixMovies(entities: List<NetflixMovieEntity>)

    @Upsert
    abstract fun upsertWatchaMovies(entities: List<WatchaMovieEntity>)

    @Query(value = """
            SELECT * FROM sync_log
            WHERE type = :syncLogType
        """
    )
    abstract suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity
    @Upsert
    abstract fun upsertSyncLog(syncLogEntity: SyncLogEntity)

    @Transaction
    open suspend fun upsertSyncLogAndMlogMovies(
        movieEntities: List<MlogMovieEntity>,
        syncLogEntity: SyncLogEntity
    ){
        upsertMlogMovies(movieEntities)
        upsertSyncLog(syncLogEntity)
    }

    @Transaction
    open suspend fun upsertSyncLogAndNetflixMovies(
        movieEntities: List<NetflixMovieEntity>,
        syncLogEntity: SyncLogEntity
    ){
        upsertNetflixMovies(movieEntities)
        upsertSyncLog(syncLogEntity)
    }

    @Transaction
    open suspend fun upsertSyncLogAndWatchaMovies(
        movieEntities: List<WatchaMovieEntity>,
        syncLogEntity: SyncLogEntity
    ){
        upsertWatchaMovies(movieEntities)
        upsertSyncLog(syncLogEntity)
    }

    @Query("""
            SELECT r.my_movie_id
            , m.adult
            , m.backdrop_path
            , m.original_title
            , m.poster_path
            , m.title
            , m.vote_average
            , m.watch_providers
            , m.rank
            , r.rated
            , r.comment
            FROM my_movie AS m
            INNER JOIN rated AS r ON m.id = r.my_movie_id
        """
    )
    abstract fun getMyRatedMovies(): Flow<List<MyRatedMoviesVO>>

}