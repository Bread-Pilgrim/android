plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.settings"
}

dependencies {
    implementation(libs.timber)
}