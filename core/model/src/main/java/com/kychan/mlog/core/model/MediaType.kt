package com.kychan.mlog.core.model

enum class MediaType(val mediaType: String) {
    TV("tv"),
    PERSON("person"),
    MOVIE("movie")
}

fun String.toMediaType(): MediaType = MediaType.values().first { type ->
    type.mediaType == this
}