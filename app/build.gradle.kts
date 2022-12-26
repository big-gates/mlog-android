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
    implementation(project(":feature:home"))
    implementation(project(":core:common"))
    implementation(project(":core:design"))
    implementation(project(":core:domain"))
    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
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