package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.*
import com.kychan.mlog.core.model.WatchProvider
import java.util.*

private const val DEFAULT_RESULT_COUNT = 20
@Dao
abstract class MovieDao {

    @Transaction
    @Query(value = """
        SELECT * 
        FROM movie as m
        LEFT JOIN watch_provider as wp on m.id = wp.movie_id
        WHERE wp.watch_provider_id = :movieTypeId
        ORDER BY wp.rank ASC
    """)
    abstract fun getMovie(movieTypeId: Int): PagingSource<Int, MovieVO>

    @Query("""
        DELETE 
        FROM movie 
        WHERE movie.id in (
            SELECT movie_id
            FROM watch_provider
            WHERE watch_provider_id = -1
        )
    """)
    abstract suspend fun clearMlogMovie()

    @Query("""
        DELETE 
        FROM movie 
        WHERE movie.id in (
            SELECT movie_id
            FROM watch_provider
            WHERE watch_provider_id = 8
        )
    """)
    abstract suspend fun clearNetflixMovie()

    @Query("""
        DELETE 
        FROM movie 
        WHERE movie.id in (
            SELECT movie_id
            FROM watch_provider
            WHERE watch_provider_id = 97
        )
    """)
    abstract suspend fun clearWatchaMovie()

    @Upsert
    abstract suspend fun upsertMovies(entities: List<MovieEntity>)

    @Upsert
    abstract suspend fun upsertTags(entities: List<TagEntity>)

    @Upsert
    abstract suspend fun upsertWatchProviders(entities: List<WatchProviderEntity>)

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
    open suspend fun updateMoviesAndSyncLogNextKey(
        movieEntities: List<MovieEntity>,
        genres: List<List<Int>>,
        syncLogType: SyncLogType,
        currentKey: Int,
    ){
        upsertMovies(movieEntities)
        val tagEntities = movieEntities.map { movie ->
            genres.map { genreIds ->
                genreIds.map { TagEntity(it, movie.id) }
            }.flatten()
        }.flatten()

        val watchProviderEntities = movieEntities.mapIndexed { index, movie ->
            when(syncLogType){
                SyncLogType.Netflix_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.NETFLIX_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
                SyncLogType.Watcha_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.WATCHA_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
                SyncLogType.Mlog_Movie -> WatchProviderEntity(
                    movieId = movie.id,
                    watchProviderId = WatchProvider.MLOG_ID,
                    rank = ((currentKey * DEFAULT_RESULT_COUNT) - DEFAULT_RESULT_COUNT) + (index + 1),
                )
            }
        }
        upsertWatchProviders(watchProviderEntities)
        upsertTags(tagEntities)
        val syncLog = getSyncLog(syncLogType)
        upsertSyncLog(syncLog.copy(nextKey = currentKey + 1))
    }
}