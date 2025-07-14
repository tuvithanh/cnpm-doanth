plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cnpm_thuchanh"
    compileSdk = 35   // ✅ cập nhật từ 34 lên 35

    defaultConfig {
        applicationId = "com.example.cnpm_thuchanh"
        minSdk = 26
        targetSdk = 35 // ✅ nên khớp với compileSdk
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}