plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:dataSourceLocal:room"))
    implementation(project(":core:dataSourceRemote:http"))
    implementation(libs.androidx.paging.runtime.ktx)

    testImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:testing"))
}