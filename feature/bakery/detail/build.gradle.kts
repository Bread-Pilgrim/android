plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.bakery.detail"
}

dependencies {
    implementation(projects.core.eventbus)

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.kt.compose)
    implementation(libs.timber)
    implementation(libs.kakao.share)    // 카카오톡 공유
}