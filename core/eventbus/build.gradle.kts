plugins {
    alias(libs.plugins.bakeroad.android.library)
}

android {
    namespace = "com.twolskone.bakeroad.core.eventbus"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.timber)
}