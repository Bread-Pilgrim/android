plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.hilt)
}

android {
    namespace = "com.twolskone.bakeroad.core.datastore"
}

dependencies {
    api(libs.androidx.datastore.preferences)
    implementation(libs.timber)
}