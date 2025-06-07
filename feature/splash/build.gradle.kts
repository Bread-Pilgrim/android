plugins {
    alias(libs.plugins.bakeroad.android.feature)
    alias(libs.plugins.bakeroad.android.library.compose)
}

android {
    namespace = "com.twolskone.bakeroad.feature.splash"
}

dependencies {
    implementation(libs.kakao.auth) // 카카오 로그인
}