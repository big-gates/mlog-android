plugins {
    id("kychan.android.application")
    id("kychan.android.hilt")
    id("kychan.android.application.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.gms.google.service)
}

android {
    defaultConfig {
        versionCode = 5
        versionName = "1.1.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(":ui:home"))

    implementation(project(":core:design"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)

    implementation(libs.material)
    implementation(libs.fragment.ktx)
    implementation(libs.activity.ktx)

    implementation(libs.browser)

    //retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    //Paging
    implementation(libs.paging.runtime.ktx)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.config.ktx)

    //Compose
    implementation(libs.material3.compose)
    implementation(libs.material2.compose)
    implementation(libs.foundation.compose)
    implementation(libs.ui.compose)
    implementation(libs.ui.tooling.preview.compose)
    debugImplementation(libs.ui.tooling.compose)
    androidTestImplementation(libs.ui.test.junit4.compose)
    debugImplementation(libs.ui.test.manifest.compose)

    implementation(libs.material.icons.core.compose)
    implementation(libs.material.icons.extended.compose)
    implementation(libs.material3.window.size.compose)
    implementation(libs.activity.compose)
    implementation(libs.androidx.lifecycle.viewModel.compose)

    implementation(libs.coil.kt.compose)

    //loging
    implementation(libs.logger)
}