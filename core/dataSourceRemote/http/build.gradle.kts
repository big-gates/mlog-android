plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.kychan.mlog.core.dataSourceRemote.http"
}

dependencies {
    implementation(project(":core:common"))
    testImplementation(project(":core:testing"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
}