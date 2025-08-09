plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.mypage"
}

dependencies {
    implementation(libs.timber)
}