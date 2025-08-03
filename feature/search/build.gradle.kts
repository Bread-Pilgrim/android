plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.search"
}

dependencies {
    implementation(libs.timber)
}