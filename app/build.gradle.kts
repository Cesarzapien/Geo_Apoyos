plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.cesar.geoapoyos3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cesar.geoapoyos3"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(files("C:\\Users\\cesar\\Downloads\\Geo-Apoyos-main\\Geo-Apoyos-main\\heresdk\\heresdk.aar"))
    implementation(files("C:\\Users\\cesar\\Downloads\\Geo-Apoyos-main\\Geo-Apoyos-main\\heresdk\\heresdk.aar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.10.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.10.0")
    implementation ("com.squareup.moshi:moshi:1.15.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
}