package com.kychan.core.testing.http

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader


fun <T>readFile(fileName: String, target: Class<T>): T{
    val path = "src/main/assets"

    val file = File("${path}/$fileName")
    val inputStream: InputStream = file.inputStream()
    val br = BufferedReader(InputStreamReader(inputStream))

    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
        .fromJson(br, target)
}