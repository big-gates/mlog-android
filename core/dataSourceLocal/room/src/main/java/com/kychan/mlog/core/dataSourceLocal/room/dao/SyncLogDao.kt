package com.kychan.mlog.core.dataSourceLocal.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
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
}