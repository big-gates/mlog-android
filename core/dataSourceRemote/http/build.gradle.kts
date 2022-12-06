plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.kychan.core.dataSourceRemote.http"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    testImplementation(project(":core:testing"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
}