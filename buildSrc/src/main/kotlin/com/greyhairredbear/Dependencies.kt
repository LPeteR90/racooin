import Android.DAGGER_GROUP
import Core.KOTLINX_GROUP
import Versions.ACCOMPANIST_VERSION
import Versions.ANDROID_MATERIAL_VERSION
import Versions.ARROW_VERSION
import Versions.BUILD_TOOLS_VERSION
import Versions.COMPOSE_VERSION
import Versions.DATASTORE_VERSION
import Versions.HILT_VERSION
import Versions.KOTEST_EXTENSIONS_ARROW_VERSION
import Versions.KOTEST_VERSION
import Versions.KOTLINX_SERIALIZATION_VERSION
import Versions.KOTLIN_COROUTINES_VERSION
import Versions.KTOR_VERSION
import Versions.LIFECYCLE_VIEWMODEL_COMPOSE_VERSION
import Versions.LOGBACK_VERSION
import Versions.MOCKK_VERSION
import Versions.PROTOBUF_VERSION
import Versions.SUPPORT_ACTIVITY_VERSION

object Versions {
    const val BUILD_TOOLS_VERSION = "7.0.2"
    const val KOTLIN_COROUTINES_VERSION = "1.5.1"

    const val ARROW_VERSION = "1.0.0"

    const val KTOR_VERSION = "1.6.2"
    const val LOGBACK_VERSION = "1.2.6"
    const val KOTLINX_SERIALIZATION_VERSION = "1.3.0"

    const val HILT_VERSION = "2.38.1"
    const val COMPOSE_VERSION = "1.0.1"
    const val SUPPORT_ACTIVITY_VERSION = "1.4.0-alpha01"
    const val APP_COMPAT_VERSION = "1.2.0"
    const val CONSTRAINT_LAYOUT_VERSION = "2.0.4"
    const val CORE_KTX_VERSION = "1.6.0"
    const val ANDROID_MATERIAL_VERSION = "1.4.0"
    const val LIFECYCLE_VIEWMODEL_COMPOSE_VERSION = "1.0.0-alpha06"
    const val DATASTORE_VERSION = "1.0.0"

    const val KOTEST_VERSION = "4.6.2"
    const val KOTEST_EXTENSIONS_ARROW_VERSION = "1.0.3"
    const val MOCKK_VERSION = "1.12.0"

    const val ANDROIDX_TEST_EXT_VERSION = "1.1.2"
    const val ANDROIDX_TEST_VERSION = "1.3.0"
    const val ESPRESSO_CORE_VERSION = "3.3.0"

    const val ACCOMPANIST_VERSION = "0.20.0"

    const val PROTOBUF_VERSION = "3.10.0"
}

object BuildPluginsVersions {
    const val ANDROID_GRADLE_PLUGIN = "7.0.2"
    const val DETEKT = "1.18.1"
    const val KOTLIN = "1.5.21"
    const val PROTOBUF = "0.8.12"
}

object Plugins {
    const val KOTLIN = "org.jetbrains.kotlin.jvm"
    const val DETEKT = "io.gitlab.arturbosch.detekt"
    const val ANDROID = "android"
    const val HILT = "dagger.hilt.android.plugin"
    const val KAPT = "kapt"

    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:$BUILD_TOOLS_VERSION"
    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildPluginsVersions.KOTLIN}"
    const val HILT_ANDROID_GRADLE_PLUGIN =
        "$DAGGER_GROUP:hilt-android-gradle-plugin:$HILT_VERSION"
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "kotlin-android"

    const val LIB_JAVA = "java-library"
    const val LIB_KOTLIN = "kotlin"
    const val SERIALIZATION = "plugin.serialization"

    const val PROTOBUF = "com.google.protobuf"
}

object Sdk {
    const val COMPILE_SDK_VERSION = 31
    const val TARGET_SDK_VERSION = 31
    const val MIN_SDK_VERSION = 26
}

object Core {
    private const val ARROW_GROUP = "io.arrow-kt"
    const val KOTLINX_GROUP = "org.jetbrains.kotlinx"

    const val STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val KOTLINX_COROUTINES =
        "$KOTLINX_GROUP:kotlinx-coroutines-core:$KOTLIN_COROUTINES_VERSION"

    const val ARROW_CORE = "$ARROW_GROUP:arrow-core:$ARROW_VERSION"
    const val ARROW_FX = "$ARROW_GROUP:arrow-fx:$ARROW_VERSION"
    const val ARROW_OPTICS = "$ARROW_GROUP:arrow-optics:$ARROW_VERSION"
    const val ARROW_SYNTAX = "$ARROW_GROUP:arrow-syntax:$ARROW_VERSION"
    const val ARROW_META = "$ARROW_GROUP:arrow-meta:$ARROW_VERSION"
}

object Utils {
    const val KOTLIN_SERIALIZATION =
        "$KOTLINX_GROUP:kotlinx-serialization-json:$KOTLINX_SERIALIZATION_VERSION"
}

object Server {
    private const val KTOR_GROUP = "io.ktor"
    private const val LOGBACK_GROUP = "ch.qos.logback"

    const val KTOR_CLIENT_CORE = "$KTOR_GROUP:ktor-client-core:$KTOR_VERSION"
    const val KTOR_CLIENT_CIO = "$KTOR_GROUP:ktor-client-cio:$KTOR_VERSION" // TODO: needed?
    const val KTOR_CLIENT_LOGGING = "$KTOR_GROUP:ktor-client-logging:$KTOR_VERSION"
    const val KTOR_CLIENT_ANDROID = "$KTOR_GROUP:ktor-client-android:$KTOR_VERSION"
    const val KTOR_CLIENT_SERIALIZATION = "$KTOR_GROUP:ktor-client-serialization:$KTOR_VERSION"

    const val LOGBACK = "$LOGBACK_GROUP:logback-classic:$LOGBACK_VERSION"
}

object Android {
    const val DAGGER_GROUP = "com.google.dagger"
    private const val DATASTORE_GROUP = "androidx.datastore"
    private const val ANDROIDX_LIFECYCLE_GROUP = "androidx.lifecycle"

    const val HILT_ANDROID = "$DAGGER_GROUP:hilt-android:$HILT_VERSION"
    const val HILT_ANDROID_COMPILER = "$DAGGER_GROUP:hilt-android-compiler:$HILT_VERSION"

    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT_VERSION}"
    const val ANDROIDX_CONSTRAINT_LAYOUT =
        "com.android.support.constraint:constraint-layout:${Versions.CONSTRAINT_LAYOUT_VERSION}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}"
    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-compose:$SUPPORT_ACTIVITY_VERSION"

    const val ANDROIDX_DATASTORE = "$DATASTORE_GROUP:datastore:$DATASTORE_VERSION"

    const val ANDROIDX_LIFECYCLE_VIEWMODEL_COMPOSE =
        "$ANDROIDX_LIFECYCLE_GROUP:lifecycle-viewmodel-compose:$LIFECYCLE_VIEWMODEL_COMPOSE_VERSION"
}

object Protobuf {
    private const val PROTOBUF_GROUP = "com.google.protobuf"

    const val PROTOBUF_JAVA_LITE = "$PROTOBUF_GROUP:protobuf-javalite:$PROTOBUF_VERSION"
    const val PROTOBUF_PROTOC = "$PROTOBUF_GROUP:protoc:$PROTOBUF_VERSION"
}

object Compose {
    private const val COMPOSE_UI_GROUP = "androidx.compose.ui"
    private const val COMPOSE_MAT_GROUP = "androidx.compose.material"
    private const val COMPOSE_FOUNDATION_GROUP = "androidx.compose.foundation"
    private const val ACCOMPANIST_GROUP = "com.google.accompanist"

    const val COMPOSE_UI = "$COMPOSE_UI_GROUP:ui:$COMPOSE_VERSION"
    const val COMPOSE_MATERIAL = "$COMPOSE_MAT_GROUP:material:$COMPOSE_VERSION"
    const val COMPOSE_UI_TOOLING = "$COMPOSE_UI_GROUP:ui-tooling:$COMPOSE_VERSION"
    const val COMPOSE_UI_TOOLING_PREVIEW = "$COMPOSE_UI_GROUP:ui-tooling-preview:$COMPOSE_VERSION"
    const val COMPOSE_FOUNDATION = "$COMPOSE_FOUNDATION_GROUP:foundation:$COMPOSE_VERSION"

    const val ACCOMPANIST_SWIPE_REFRESH =
        "$ACCOMPANIST_GROUP:accompanist-swiperefresh:$ACCOMPANIST_VERSION"
}

object GoogleLibs {
    const val ANDROID_MATERIAL = "com.google.android.material:material:$ANDROID_MATERIAL_VERSION"
}

object Testing {
    private const val KOTEST_GROUP = "io.kotest"
    private const val KOTEST_EXTENSIONS_GROUP = "io.kotest.extensions"
    const val KOTEST_RUNNER = "$KOTEST_GROUP:kotest-runner-junit5-jvm:$KOTEST_VERSION"
    const val KOTEST_JUNIT_RUNNER = "$KOTEST_GROUP:kotest-runner-junit5:$KOTEST_VERSION"
    const val KOTEST_ASSERTIONS = "$KOTEST_GROUP:kotest-assertions-core-jvm:$KOTEST_VERSION"
    const val KOTEST_EXTENSIONS_ARROW =
        "$KOTEST_EXTENSIONS_GROUP:kotest-assertions-arrow:$KOTEST_EXTENSIONS_ARROW_VERSION"
    const val KOTEST_PROPERTIES = "$KOTEST_GROUP:kotest-property-jvm:$KOTEST_VERSION"

    const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"

    const val KOTLINX_COROUTINES_TEST =
        "$KOTLINX_GROUP:kotlinx-coroutines-test:$KOTLIN_COROUTINES_VERSION"
}

object AndroidTesting {
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST_VERSION}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST_VERSION}"
    const val ANDROIDX_TEST_EXT_JUNIT =
        "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT_VERSION}"
    const val ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE_VERSION}"
}
