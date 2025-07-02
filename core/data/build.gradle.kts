plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.hilt)
}

android {
    namespace = "com.twolskone.bakeroad.core.data"
}

dependencies {
    api(projects.core.remote)
    api(projects.core.datastore)
    implementation(projects.core.domain)
}