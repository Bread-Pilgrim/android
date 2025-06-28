plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.twolskone.bakeroad.core.remote"
}

dependencies {
    implementation(projects.core.common.kotlin)
    implementation(projects.core.datastore)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.timber)
}

secrets {

}