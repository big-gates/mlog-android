plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core:testing"))
}