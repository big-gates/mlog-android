package com.kychan.mlog.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kychan.mlog.core.common.extenstions.toDate
import com.kychan.mlog.core.data.mapper.genres
import com.kychan.mlog.core.data.mapper.toMovieEntity
import com.kychan.mlog.core.dataSourceLocal.room.datasource.RoomDataSource
import com.kychan.mlog.core.dataSourceLocal.room.model.MovieVo
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import com.kychan.mlog.core.dataSourceRemote.http.datasource.TMDBDataSource
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MLogMovieMediator(
    private val roomDataSource: RoomDataSource,
    private val tmdbDataSource: TMDBDataSource,
    ):RemoteMediator<Int, MovieVo>() {

    companion object{
        const val END_PAGE = 500
    }

    override suspend fun initialize(): InitializeAction {
        val latestUpdate = roomDataSource.getSyncLog(SyncLogType.Mlog_Movie).updatedAt.toDate().time
        val cacheTime = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
        return if(System.currentTimeMillis() >= latestUpdate + cacheTime) {
            roomDataSource.clearMlogMoviesUpdateSyncLogUpdatedAt()
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else InitializeAction.SKIP_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieVo>
    ): MediatorResult {
        try {
            val syncLog = roomDataSource.getSyncLog(SyncLogType.Mlog_Movie)

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> syncLog.nextKey
            }?: 1

            val data = tmdbDataSource.getMoviePopular(
                page = loadKey,
                language = Language.KR,
                watchRegion = WatchRegion.KR
            )

            roomDataSource.updateMoviesAndSyncLogNextKey(
                movieEntities = data.toMovieEntity(),
                genres = data.genres(),
                syncLogType = syncLog.type,
                currentKey = loadKey
            )
            return MediatorResult.Success(endOfPaginationReached = loadKey + 1 > END_PAGE)
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

}