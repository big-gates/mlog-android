plugins {
    id("kychan.android.library")
    id("kychan.android.library.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace = "com.kychan.mlog.core.designsystem"
}

dependencies {
    implementation(libs.androidx.ui.android)
    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.icons.extended)
    api(libs.androidx.compose.material2)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.a914.gowtham.compose.ratingbar)
}