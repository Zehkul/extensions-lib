import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.tapmoc)
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.injekt)
    implementation(libs.kotlinx.serialization.json)
}

kotlin {
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {}
}

@Suppress("DEPRECATION")
android {
    namespace = "eu.kanade.tachiyomi.extensions"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }
}

spotless {
    val ktlintVersion = libs.ktlint.cli.get().version
    kotlin {
        target("src/**/*.kt")
        ktlint(ktlintVersion)
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint(ktlintVersion)
    }
}

tapmoc {
    java(17)
}

mavenPublishing {
    coordinates("com.github.zehkul", "extensions-lib", "1.6.0-bidirsync.1")

    pom {
        name.set("extensions-lib")
        description.set("Stubs used for extensions in Tachiyomi/Mihon/Komikku; includes ProgressAwareSource (1.6.0-bidirsync.1).")
        url.set("https://github.com/Zehkul/extensions-lib")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
                distribution.set("repo")
            }
        }

        organization {
            name.set("Zehkul")
            url.set("https://github.com/Zehkul")
        }

        scm {
            connection.set("scm:git:git://github.com/Zehkul/extensions-lib.git")
            url.set("https://github.com/Zehkul/extensions-lib")
        }
    }
}

