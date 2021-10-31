import Versions.JVM_TARGET
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.ANDROID_APPLICATION) version BuildPluginsVersions.ANDROID_GRADLE_PLUGIN apply false
    kotlin(Plugins.ANDROID) version BuildPluginsVersions.KOTLIN apply false
    id(Plugins.DETEKT) version BuildPluginsVersions.DETEKT
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(Plugins.ANDROID_GRADLE_PLUGIN)
        classpath(Plugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Plugins.HILT_ANDROID_GRADLE_PLUGIN)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply("config/git-hooks/gitHooks.gradle.kts")
apply("config/git-hooks/preCommit.gradle.kts")

allprojects {
    group = PUBLISHING_GROUP
    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JVM_TARGET
        }
    }
}

subprojects {
    apply {
        plugin(Plugins.DETEKT)
    }

    detekt {
        allRules = false
        buildUponDefaultConfig = true
        config = rootProject.files("config/detekt/detekt.yml")

        reports {
            html.enabled = true
            xml.enabled = true
            txt.enabled = true
        }
    }
}

tasks.register("clean").configure {
    delete("build")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}
