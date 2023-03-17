package com.kychan.mlog.core.common.extenstions

import java.text.SimpleDateFormat
import java.util.*

private val syncDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun String.toDate(): Date =
    syncDateFormat.parse(this) ?: throw IllegalArgumentException("unsupported time format")

fun Long.toSyncDateFormat(): String = syncDateFormat.format(Date(this))