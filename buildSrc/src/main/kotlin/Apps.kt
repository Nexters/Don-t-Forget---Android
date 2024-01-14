import org.gradle.api.JavaVersion

object Apps {
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34

    val javaCompileOption = JavaVersion.VERSION_17
    const val jvmTarget = "17"
}