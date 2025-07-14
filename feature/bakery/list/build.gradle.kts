plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.bakery.list"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.timber)
}