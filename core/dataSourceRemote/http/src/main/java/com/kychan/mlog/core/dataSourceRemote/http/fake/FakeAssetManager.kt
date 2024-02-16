package com.kychan.mlog.core.dataSourceRemote.http.fake

import java.io.InputStream

fun interface FakeAssetManager {
    fun open(fileName: String): InputStream
}