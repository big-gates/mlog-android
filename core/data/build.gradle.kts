plugins {
    id("kychan.android.library")
    id("kychan.android.hilt")
}

android {
    namespace = "com.kychan.mlog.core.data"

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:dataSourceRemote:http"))

    testImplementation(project(":core:testing"))

    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)
}