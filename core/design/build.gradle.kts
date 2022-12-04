plugins {
    id("kychan.android.library")
    id("kychan.android.library.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    namespace = "com.kychan.core.designsystem"
}

dependencies {
    implementation(libs.core.ktx)
    api(libs.foundation.compose)
    api(libs.material.icons.extended.compose)
    api(libs.material2.compose)
    api(libs.material3.compose)
    debugApi(libs.ui.tooling.compose)
    api(libs.ui.tooling.preview.compose)
}