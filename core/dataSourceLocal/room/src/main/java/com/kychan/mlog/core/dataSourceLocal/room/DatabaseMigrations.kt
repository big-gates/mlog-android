package com.kychan.mlog.core.dataSourceLocal.room

import androidx.room.DeleteTable
import androidx.room.migration.AutoMigrationSpec

object DatabaseMigrations {

    @DeleteTable.Entries(
        DeleteTable("movie_table")
    )
    class Schema2to3: AutoMigrationSpec
}