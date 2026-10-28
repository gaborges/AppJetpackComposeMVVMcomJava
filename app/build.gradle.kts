plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.ifsul.jet.appjetcomposemvvmcomjava"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.ifsul.jet.appjetcomposemvvmcomjava"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation("androidx.compose.material3:material3:1.3.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:3.6.0")
    // Http3 Logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Adicionar para ter acesso a standard icons: Home, Menu, and Settings
    implementation("androidx.compose.material:material-icons-core")
    // OPTIONAL: Add this if you want access to ALL Material icons
    // (Note: This is a much larger library, so only use it if you need icons outside the core set)
    implementation("androidx.compose.material:material-icons-extended")
    // Coil 3 image loading library to your Android or Compose Multiplatform project,
    implementation("io.coil-kt:coil-compose:2.7.0")
    // Allows Compose to observe LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
}