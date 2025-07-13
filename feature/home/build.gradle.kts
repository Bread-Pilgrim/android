plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.home"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.coil.kt.compose)
}