plugins {
    alias(libs.plugins.bakeroad.android.feature)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.twolskone.bakeroad.feature.login"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.common.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kakao.auth) // 카카오 로그인
    implementation(libs.timber)
}