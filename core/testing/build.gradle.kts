plugins {
    id("kychan.android.library")
    id("kychan.android.library.compose")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.testing"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:dataSourceRemote:http"))
    api(project(":core:model"))

    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.hilt.android.testing)

    implementation(libs.retrofit.gson)
    debugApi(libs.androidx.compose.ui.test.manifest)
}