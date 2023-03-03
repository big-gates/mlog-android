plugins {
    id("kychan.android.library.compose")
    id("kychan.android.feature")
}

android {
    namespace = "com.kychan.mlog.feature.mypage"
}

dependencies {
    implementation(libs.accompanist.pager.layout)
    implementation(libs.accompanist.pager.indicators)
}