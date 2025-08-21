plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.report"
}

dependencies {
    implementation(libs.gson)
    implementation(libs.timber)
}