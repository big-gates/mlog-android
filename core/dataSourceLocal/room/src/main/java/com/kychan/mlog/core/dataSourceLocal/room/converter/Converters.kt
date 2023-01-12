package com.kychan.mlog.core.dataSourceLocal.room.converter

import androidx.room.TypeConverter
import com.kychan.mlog.core.model.WatchProviders

class WatchProvidersConverters {

    @TypeConverter
    fun toWatchProvider(value: String): WatchProviders = WatchProviders.values().first { type ->
        type.id == value
    }

    @TypeConverter
    fun fromWatchProvider(value: WatchProviders): String = value.id
}