plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.search"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.timber)
}