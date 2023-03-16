plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.domain"

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.paging.runtime.ktx)

    testImplementation(project(":core:testing"))
}