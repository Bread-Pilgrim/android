plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.android.library.compose)
}

android {
    namespace = "com.twolskone.bakeroad.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.common.kotlin)

    implementation(libs.coil.kt.compose)
}