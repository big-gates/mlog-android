package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType

@Dao
abstract class SyncLogDao {

    @Query(value = """
            SELECT * FROM sync_log
            WHERE type = :syncLogType
        """
    )
    abstract suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity

    @Upsert
    abstract suspend fun upsertSyncLog(syncLogEntity: SyncLogEntity)

    open suspend fun clearSyncLog(syncLogType: SyncLogType){
        val syncLog = getSyncLog(syncLogType)
        upsertSyncLog(syncLog.copy(
            nextKey = 1,
            updatedAt = System.currentTimeMillis().toDateFormat()
        ))
    }
}