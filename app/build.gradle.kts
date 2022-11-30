plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.gms.google.service)
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kychan.mlog"
        minSdk = 24
        targetSdk = 31
        versionCode = 5
        versionName = "1.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }

    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.kychan.mlog"
}

dependencies {

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
    kapt(libs.glide.compiler)

    //Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    //Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //Paging
    implementation(libs.paging.runtime.ktx)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.config.ktx)

    //Compose
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

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
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.coil.compose)

    //loging
    implementation(libs.logger)
}