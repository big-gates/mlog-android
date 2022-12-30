package com.kychan.mlog.core.model

import kotlinx.serialization.SerialName

enum class WatchProviders(id: String) {
    @SerialName("8") Netflix("8"),
    @SerialName("97") Watcha("97")
}