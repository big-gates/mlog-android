package com.kychan.mlog.core.model

import kotlinx.serialization.SerialName

enum class Language(ISO_639_1_Code : String) {
    @SerialName("ko-KR") KR("ko-KR")
}