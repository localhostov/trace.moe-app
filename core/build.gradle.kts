plugins {
    `kotlin-android`
    `android-library`
}

setupModuleForAndroidxCompose()

android {
    compileSdk = 34
    namespace = "core"
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.androidx.datastore)
}