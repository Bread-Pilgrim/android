plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.twolskone.bakeroad.core.remote"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.exception)
    implementation(projects.core.common.kotlin)
    implementation(projects.core.common.android)
    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(projects.core.eventbus)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.timber)
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}