plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Required for Kotlin annotation processing
}

android {
    namespace = "com.example.nirbhaya"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nirbhaya"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Ola Maps SDK
    implementation(files("libs/olamaps.aar"))

    // Maplibre (choose one version, preferably the latest)
    implementation("org.maplibre.gl:android-sdk:10.2.0") // Using 10.2.0
    implementation("org.maplibre.gl:android-plugin-annotation-v9:1.0.0")
    implementation("org.maplibre.gl:android-plugin-markerview-v9:1.0.0")

    // Required for Ola-MapsSdk (remove redundant entries)
    implementation("com.moengage:moe-android-sdk:12.6.01")
    implementation("org.maplibre.gl:android-sdk-directions-models:5.9.0")
    implementation("org.maplibre.gl:android-sdk-services:5.9.0")
    implementation("org.maplibre.gl:android-sdk-turf:5.9.0")

    // Lifecycle components (replace deprecated lifecycle-extensions)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // Updated version
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // Updated version

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


}


