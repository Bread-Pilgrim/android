plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.onboard"
}

dependencies {
    implementation(libs.timber)
}