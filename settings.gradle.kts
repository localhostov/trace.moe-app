enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = "https://maven.google.com")
        maven(url = "https://jitpack.io")

        google()
        mavenCentral()
    }
}

plugins {
    id("com.dropbox.focus") version "0.4.0"
}

configure<com.dropbox.focus.FocusExtension> {
    allSettingsFileName.set("includes.gradle.kts")
    focusFileName.set(".focus")
}