plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.core.dataSourceLocal.room"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
}