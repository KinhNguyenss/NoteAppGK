plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.noteapppppppppppppp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.noteapppppppppppppp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

        // Jetpack Compose
        implementation ("androidx.activity:activity-compose:1.8.2")
        implementation ("androidx.compose.ui:ui:1.6.4")
        implementation ("androidx.compose.material3:material3:1.2.1")
        implementation( "androidx.compose.runtime:runtime-livedata:1.6.4")

        // ViewModel
        implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
        implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
        // Room
        implementation ("androidx.room:room-runtime:2.6.1")
        kapt ("androidx.room:room-compiler:2.6.1")
        implementation ("androidx.room:room-ktx:2.6.1")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        // Hilt
        implementation ("com.google.dagger:hilt-android:2.48")
        kapt ("com.google.dagger:hilt-compiler:2.48")

        // Coil for image loading
        implementation ("io.coil-kt:coil-compose:2.4.0")

        // Navigation Compose
        implementation ("androidx.navigation:navigation-compose:2.7.7")
}