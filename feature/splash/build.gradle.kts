plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.splash"
}

dependencies {
    implementation(libs.kakao.auth) // 카카오 로그인
}