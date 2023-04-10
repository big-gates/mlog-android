package com.kychan.mlog.core.common.extenstions

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
fun String.toDate(): Date =
    dateFormat.parse(this) ?: throw IllegalArgumentException("unsupported time format")

fun Long.toDateFormat(): String = dateFormat.format(Date(this))

fun Long.toDateTimeFormat(): String = dateTimeFormat.format(Date(this))