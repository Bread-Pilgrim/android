plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.bakery.detail"
}

dependencies {
    implementation(libs.coil.kt.compose)
    implementation(libs.timber)
}