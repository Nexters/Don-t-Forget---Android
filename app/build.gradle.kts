plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "nexters.hyomk.dontforget"
    compileSdk = Apps.compileSdk

    defaultConfig {
        applicationId = "nexters.hyomk.dontforget"
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = Apps.javaCompileOption
        targetCompatibility = Apps.javaCompileOption
    }
    kotlinOptions {
        jvmTarget = Apps.jvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.core}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime_ktx}")
    implementation("androidx.activity:activity-compose:${Versions.compose}")
    implementation(platform("androidx.compose:compose-bom:${Versions.compose_bom}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:${Versions.junit_test}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.junit_android_test}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso_core}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${Versions.compose_bom}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${Versions.compose_lifecycle}")
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-compiler:${Versions.hilt}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.hilt_navigation_compose}")
    implementation("androidx.navigation:navigation-compose:${Versions.compose_navigation}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}")
    implementation("com.squareup.retrofit2:converter-scalars:${Versions.retrofit}")
    implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp_urlconnection}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}")
    implementation("com.jakewharton.timber:timber:${Versions.timber}")
    implementation("androidx.datastore:datastore-preferences-core:${Versions.datastore}")
    implementation("androidx.datastore:datastore-preferences:${Versions.datastore}")
    implementation("io.coil-kt:coil-compose:${Versions.coil}")
}