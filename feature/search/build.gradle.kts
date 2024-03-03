plugins {
    id("kychan.android.library.compose")
    id("kychan.android.feature")
}

android {
    namespace = "com.kychan.mlog.feature.search"
}

dependencies{
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(project(":feature:movie_modal"))
}
