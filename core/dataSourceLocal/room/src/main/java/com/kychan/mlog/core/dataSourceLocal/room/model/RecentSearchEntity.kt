package com.kychan.mlog.core.dataSourceLocal.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kychan.mlog.core.model.RecentSearch

@Entity(
    indices = [Index(value = ["id", "text"], unique = true)],
    tableName = "recent_search"
)
data class RecentSearchEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val text: String,
    @ColumnInfo("created_at") val createdAt: String,
)

fun RecentSearchEntity.toDomain(): RecentSearch = RecentSearch(
    id = id?: throw IllegalArgumentException("RecentSearchEntity ID is NULL this is WHAT THE HELL"),
    text = text
)