package com.kychan.mlog.core.data.testdouble

import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class FakeSyncLogDao: SyncLogDao {
    private val entitiesStateFlow = MutableStateFlow(listOf(
        SyncLogEntity(
            id = 1,
            type = SyncLogType.Netflix_Movie,
            nextKey = 1,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
            updatedAt = (System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000)).toDateTimeFormat(),
        ),
        SyncLogEntity(
            id = 2,
            type = SyncLogType.Mlog_Movie,
            nextKey = 1,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
            updatedAt = (System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000)).toDateTimeFormat(),
        ),
        SyncLogEntity(
            id = 3,
            type = SyncLogType.Watcha_Movie,
            nextKey = 1,
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
            updatedAt = (System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000)).toDateTimeFormat(),
        )
    ))
    override suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity {
        return checkNotNull(entitiesStateFlow.first().find { it.type == syncLogType })
    }

    override suspend fun upsertSyncLog(syncLogEntity: SyncLogEntity) {
        entitiesStateFlow.update { syncLogList ->
            syncLogList.map { syncLog ->
                if(syncLog.type == syncLogEntity.type) syncLogEntity
                else syncLog
            }
        }
    }
}