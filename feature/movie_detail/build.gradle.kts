plugins {
    id("kychan.android.library.compose")
    id("kychan.android.feature")
}

android {
    namespace = "com.kychan.mlog.feature.movie_detail"
}

dependencies {

    implementation(libs.a914.gowtham.compose.ratingbar)
    implementation(project(":feature:movie_modal"))

}