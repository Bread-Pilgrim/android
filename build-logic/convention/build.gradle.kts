import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.twolskone.bakeroad.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    val conventionPluginClasses = listOf(
        "android.application" to "AndroidApplicationConventionPlugin",
        "android.application.compose" to "AndroidApplicationComposeConventionPlugin",
        "hilt" to "HiltConventionPlugin",
        "jvm.library" to "JvmLibraryConventionPlugin"
    )
    plugins {
        conventionPluginClasses.forEach { pluginClass ->
            registerPlugins(pluginClass)
        }
    }
}

/**
 * Register plugins
 * @param pair (plugin name, class name)
 */
fun NamedDomainObjectContainer<PluginDeclaration>.registerPlugins(pair: Pair<String, String>) {
    val (pluginName, className) = pair
    register(pluginName) {
        id = "bakeload.$pluginName"
        implementationClass = className
    }
}