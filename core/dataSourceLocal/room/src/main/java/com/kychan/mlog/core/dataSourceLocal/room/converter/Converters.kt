package com.kychan.mlog.core.dataSourceLocal.room.converter

import androidx.room.TypeConverter
import com.kychan.mlog.core.model.WatchProvider

class WatchProvidersConverters {

    @TypeConverter
    fun toWatchProvider(value: String): WatchProvider = WatchProvider.values().first { type ->
        type.id == value
    }

    @TypeConverter
    fun fromWatchProvider(value: WatchProvider): String = value.id
}