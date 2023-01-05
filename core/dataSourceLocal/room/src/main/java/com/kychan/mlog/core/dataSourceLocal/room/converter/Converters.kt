package com.kychan.mlog.core.dataSourceLocal.room.converter

import androidx.room.TypeConverter
import com.kychan.mlog.core.model.WatchProviders

class WatchProvidersConverters {

    @TypeConverter
    fun toWatchProvider(value: String): WatchProviders = enumValueOf(value)

    @TypeConverter
    fun fromWatchProvider(value: WatchProviders): String = value.id
}