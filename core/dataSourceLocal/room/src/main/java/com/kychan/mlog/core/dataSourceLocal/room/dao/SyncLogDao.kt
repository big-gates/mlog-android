package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType

@Dao
interface SyncLogDao {

    @Query(value = """
            SELECT * FROM sync_log
            WHERE type = :syncLogType
        """
    )
    suspend fun getSyncLog(syncLogType: SyncLogType): SyncLogEntity

    @Upsert
    suspend fun upsertSyncLog(syncLogEntity: SyncLogEntity)
}