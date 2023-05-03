plugins {
    id("kychan.android.library.compose")
    id("kychan.android.feature")
}

android {
    namespace = "com.kychan.mlog.feature.movie_modal"
}

dependencies {

    implementation(libs.a914.gowtham.compose.ratingbar)

}