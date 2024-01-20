// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath(
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android_gradle_plugin}",
        )
        classpath("com.google.gms:google-services:${Versions.google_services}")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2" apply true
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.3.2" apply true
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        android.set(true)
        disabledRules.set(setOf("import-ordering", "package-name"))
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }
}
