plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.dataSourceLocal.room"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    testImplementation(project(":core:testing"))
}