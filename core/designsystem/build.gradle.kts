plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.android.library.compose)
}

android {
    namespace = "com.twolskone.bakeroad.core.designsystem"
}

dependencies {

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
}
