plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.intro"
}

dependencies {
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.kakao.auth) // 카카오 로그인
    implementation(libs.timber)
}