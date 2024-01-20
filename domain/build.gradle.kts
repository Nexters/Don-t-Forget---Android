plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Apps.javaCompileOption
    targetCompatibility = Apps.javaCompileOption
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.inject:javax.inject:${Versions.javax_inject}")
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_core}"
    )

    // alternatively - without Android dependencies for tests
    implementation("androidx.paging:paging-common:${Versions.paging_common}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}")
    implementation("com.squareup.retrofit2:converter-scalars:${Versions.retrofit}")

    // okHttp
    implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp_urlconnection}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}")
}
