plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.photoviewer"
}

dependencies {

    implementation(libs.coil.kt.compose)
    implementation(libs.timber)
    implementation(libs.photo.compose)
    implementation(libs.zoomable)
}