plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.mybakery"
}

dependencies {
    implementation(projects.core.eventbus)

    implementation(libs.androidx.paging.compose)
    implementation(libs.timber)
}