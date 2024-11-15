plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.utsa_classroom_finder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.utsa_classroom_finder"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "35.0.0"
}

dependencies {
    implementation("org.osmdroid:osmdroid-android:6.1.20")
    implementation("com.android.volley:volley:1.2.1")
    //implementation (libs.osmbonuspack)
    implementation ("androidx.security:security-crypto:1.1.0-alpha03")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}