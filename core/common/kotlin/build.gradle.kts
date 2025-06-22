plugins {
    alias(libs.plugins.bakeroad.jvm.library)
    alias(libs.plugins.bakeroad.hilt)
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit)
}