plugins {
    id("kychan.android.library")
    id("kychan.android.library.compose")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.core.testing"

}

dependencies {
    implementation(project(":core:common"))

    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.hilt.android.testing)

    debugApi(libs.androidx.compose.ui.test.manifest)
}