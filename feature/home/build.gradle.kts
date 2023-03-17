plugins {
    id("kychan.android.library.compose")
    id("kychan.android.feature")
}

android {
    namespace = "com.kychan.mlog.feature.home"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)
}