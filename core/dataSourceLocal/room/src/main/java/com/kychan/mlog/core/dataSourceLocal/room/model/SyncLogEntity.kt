package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sync_log",
    indices = [Index(value = ["type"], unique = true)]
)
data class SyncLogEntity(
    @PrimaryKey(true) val id: Int = 0,
    val type: SyncLogType,
    @ColumnInfo("next_key") val nextKey: Int,
    @ColumnInfo("created_at") val createdAt: String,
    @ColumnInfo("updated_at") val updatedAt: String,
)

enum class SyncLogType{
    Netflix_Movie,
    Watcha_Movie,
    Mlog_Movie,
}