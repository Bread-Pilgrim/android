plugins {
    alias(libs.plugins.bakeroad.android.feature)
}

android {
    namespace = "com.twolskone.bakeroad.feature.badge"
}

dependencies {
    implementation(projects.core.eventbus)
    
    implementation(libs.timber)
}