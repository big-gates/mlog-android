package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.Movie
import com.kychan.mlog.core.model.WatchProvider

@Entity(
    tableName = "watch_provider",
    indices = [Index(value = ["id","watch_provider"], unique = true)]
)
data class WatchProviderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("watch_provider") val watchProvider: WatchProvider,
)
