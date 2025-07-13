plugins {
    alias(libs.plugins.bakeroad.android.library)
}

android {
    namespace = "com.twolskone.bakeroad.core.exception"
}

dependencies {
    implementation(libs.retrofit)
}