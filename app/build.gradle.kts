import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    kotlin(Plugins.KAPT)
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.PROTOBUF) version BuildPluginsVersions.PROTOBUF
    id(Plugins.HILT)
}

val installGitHooks by rootProject.tasks.existing
val preBuild by tasks.existing {
    dependsOn(installGitHooks)
}

android {
    compileSdk = Sdk.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Sdk.MIN_SDK_VERSION
        targetSdk = Sdk.TARGET_SDK_VERSION

        applicationId = AppInfo.APP_ID
        versionCode = AppInfo.APP_VERSION_CODE
        versionName = AppInfo.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_VERSION
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":apiclient")))

    implementation(Core.KOTLINX_COROUTINES)

    implementation(Compose.COMPOSE_UI)
    implementation(Compose.COMPOSE_MATERIAL)
    implementation(Compose.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Compose.COMPOSE_FOUNDATION)

    implementation(Compose.ACCOMPANIST_SWIPE_REFRESH)

    implementation(Android.ANDROIDX_LIFECYCLE_VIEWMODEL_COMPOSE)

    implementation(Protobuf.PROTOBUF_JAVA_LITE)
    implementation(Android.ANDROIDX_DATASTORE)

    implementation(Android.ANDROIDX_APPCOMPAT)
    implementation(Android.ANDROIDX_CORE_KTX)
    implementation(Android.ANDROIDX_ACTIVITY)
    implementation (GoogleLibs.ANDROID_MATERIAL)

    implementation(Android.HILT_ANDROID)
    kapt(Android.HILT_ANDROID_COMPILER)

    testImplementation(Testing.KOTEST_RUNNER)
    testImplementation(Testing.KOTEST_JUNIT_RUNNER)
    testImplementation(Testing.KOTEST_ASSERTIONS)
    testImplementation(Testing.KOTEST_EXTENSIONS_ARROW)
    testImplementation(Testing.KOTEST_PROPERTIES)
    testImplementation(Testing.MOCKK)
    testImplementation(Testing.KOTLINX_COROUTINES_TEST)

    androidTestImplementation(AndroidTesting.ANDROIDX_TEST_RUNNER)
    androidTestImplementation(AndroidTesting.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTesting.ANDROIDX_TEST_RULES)
    androidTestImplementation(AndroidTesting.ESPRESSO_CORE)

    debugImplementation(Compose.COMPOSE_UI_TOOLING)
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = Protobuf.PROTOBUF_PROTOC
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
