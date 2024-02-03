import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.AppExtension

private fun BaseExtension.setupAndroid() {
    compileSdkVersion(34)
    defaultConfig {
        minSdk = 26
        targetSdk = 34
        resourceConfigurations += setOf("ru", "en")
        vectorDrawables.useSupportLibrary = true

        versionCode = (System.currentTimeMillis() / 1000).toInt()
        versionName = "1.0.0"
    }
}

private fun BaseExtension.setupApplicationAndroid() {
    setupAndroid()

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions.resources.excludes += setOf(
        "META-INF/**"
    )
}

fun Project.setupApplicationModule() {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val composeCompilerVersion = libs.findVersion("composeCompiler").get().requiredVersion

    androidExtension.apply {
        setupApplicationAndroid()

        buildFeatures.apply {
            compose = true
            buildConfig = true
        }

        signingConfigs.create("release") {
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }

        buildTypes.getByName("release") {
            setSigningConfig(signingConfigs.getByName("release"))
        }

        composeOptions {
            kotlinCompilerExtensionVersion = composeCompilerVersion
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}

fun Project.setupModuleForAndroidxCompose() {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val composeCompilerVersion = libs.findVersion("composeCompiler").get().requiredVersion

    androidExtension.apply {
        setupAndroid()

        buildFeatures.apply {
            compose = true
            buildConfig = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = composeCompilerVersion
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}