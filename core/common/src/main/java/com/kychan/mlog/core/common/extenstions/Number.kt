package com.kychan.mlog.core.common.extenstions

import kotlin.math.roundToInt

fun Double.roundToTheFirstDecimal(): Double = (this * 10).roundToInt() / 10.0
