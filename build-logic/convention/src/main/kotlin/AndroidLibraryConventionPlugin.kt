import com.android.build.gradle.LibraryExtension
import com.twolskone.bakeroad.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.apply {
                    targetSdk = 35
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                // The resource prefix is derived from the module name,
                // so resources index "core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(1)
                    .distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
            }
        }
    }
}