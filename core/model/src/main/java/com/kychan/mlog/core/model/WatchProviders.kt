package com.kychan.mlog.core.model

import kotlinx.serialization.SerialName

enum class WatchProviders(val id: String) {
    @SerialName("8") Netflix("8"),
    @SerialName("97") Watcha("97"),
    @SerialName("-1") None("-1")
}