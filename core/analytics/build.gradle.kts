plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.android.library.compose)
    alias(libs.plugins.bakeroad.hilt)
}

android {
    namespace = "com.twolskone.bakeroad.core.analytics"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.timber)
}