package com.kychan.mlog.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kychan.mlog.core.data.mapper.toNetflixMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.NetflixMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchProvider
import com.kychan.mlog.core.model.WatchRegion
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPagingApi::class)
class NetflixMovieMediator(
    private val roomDataSource: RoomDataSource,
    private val tmdbDataSource: TMDBDataSource,
):RemoteMediator<Int, NetflixMovieEntity>() {

    companion object{
        const val END_PAGE = 500
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NetflixMovieEntity>
    ): MediatorResult {
        try {
            val syncLog = roomDataSource.getSyncLog(SyncLogType.Netflix_Movie)

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> syncLog.nextKey
            }?: 1


            val data = tmdbDataSource.getMoviePopularWithProvider(
                page = loadKey,
                language = Language.KR,
                watchRegion = WatchRegion.KR,
                withWatchProvider = WatchProvider.Netflix
            ).toNetflixMovieEntity(loadKey)

            roomDataSource.upsertSyncLogAndNetflixMovies(
                movieEntities = data,
                syncLogEntity = syncLog.copy(
                    nextKey = loadKey + 1,
                    updatedAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(Date(System.currentTimeMillis())
                    )
                )
            )
            return MediatorResult.Success(endOfPaginationReached = loadKey + 1 > END_PAGE)
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

}