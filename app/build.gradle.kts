plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services) // keep google services plugin
}

android {

    namespace = "com.example.prog7314outabout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.prog7314outabout"
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
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Fragments
    implementation("androidx.fragment:fragment-ktx:1.8.4")

    // Required for Google Fonts
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // 🔥 Firebase BOM (manages all Firebase versions consistently)
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase dependencies (no version numbers needed when using BoM)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
// Firebase Auth
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation(libs.firebase.appcheck.debug)
// Google Sign-In

    // Unit testing
    testImplementation("junit:junit:4.13.2")

    // Android testing
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}


//dependencies {
//    implementation("androidx.core:core-ktx:1.15.0")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
//
//    // Fragments
//    implementation("androidx.fragment:fragment-ktx:1.8.4")
//
//    // Required for Google Fonts
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//
//    // Navigation Component
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
//
//    // 🔥 Firebase BOM (manages all Firebase versions consistently)
//    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
//
//    // Firebase dependencies (no version numbers needed)
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-firestore-ktx")
//    implementation("com.google.firebase:firebase-database-ktx")
//    implementation("com.google.firebase:firebase-storage-ktx")
//    implementation(libs.firebase.auth.ktx)
//    implementation("com.google.firebase:firebase-auth-ktx:22.2.2")
//
//    // Unit testing
//    testImplementation("junit:junit:4.13.2")
//
//    // Android testing
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//}

