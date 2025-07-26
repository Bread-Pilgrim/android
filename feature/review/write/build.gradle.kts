plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.review.write"
}

dependencies {
    implementation(libs.timber)
}