package com.kychan.mlog.core.dataSourceLocal.room.converter

import androidx.room.TypeConverter
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType

object SyncLogTypeConverter {

    @TypeConverter
    fun toSyncLogType(value: String): SyncLogType = SyncLogType.values().first { type ->
        type.name == value
    }

    @TypeConverter
    fun fromSyncLogType(value: SyncLogType): String = value.name

}