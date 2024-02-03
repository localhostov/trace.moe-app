plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

buildscript {
    repositories {
        maven(url = "https://maven.google.com")
        maven(url = "https://jitpack.io")

        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.plugin.kotlin)
    }
}