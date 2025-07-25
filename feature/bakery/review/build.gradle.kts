plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.bakery.review"
}

dependencies {
    implementation(libs.timber)
}