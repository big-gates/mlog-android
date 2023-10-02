package com.kychan.mlog.core.dataSourceRemote.http

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


fun <T>readFile(clazz: Class<*>, fileName: String, target: Class<T>): T{
    val inputStream: InputStream? = clazz.getResourceAsStream(fileName)
    val br = BufferedReader(InputStreamReader(inputStream))

    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
        .fromJson(br, target)
}