plugins {
    alias(libs.plugins.bakeroad.android.library)
}

android {
    namespace = "com.twolskone.bakeroad.core.navigator"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}