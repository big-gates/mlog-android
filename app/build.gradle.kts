plugins {
    id("kychan.android.application")
    id("kychan.android.hilt")
    id("kychan.android.application.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.gms.google.service)
}

android {
    defaultConfig {
        versionCode = 9
        versionName = "2.0.2" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-gson.pro",
                "proguard-retrofit2.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.kychan.mlog"
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:mypage"))
    implementation(project(":feature:search"))
    implementation(project(":feature:movie_detail"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:design"))
    implementation(project(":core:domain"))
    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.activity.ktx)

    //Firebase
    // 추 후 스플래쉬 구현시 강제업데이트, 버전 체크 등 구현 해야함
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics.ktx)
//    implementation(libs.firebase.config.ktx)

    implementation(libs.material)
    //Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material2)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.lifecycle.viewModel.compose)

    implementation(libs.coil.kt.compose)

    //Navigation
    androidTestImplementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    //loging
    implementation(libs.logger)
}