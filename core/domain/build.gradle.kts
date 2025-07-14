plugins {
    alias(libs.plugins.bakeroad.jvm.library)
    alias(libs.plugins.bakeroad.hilt)
}

dependencies {
    implementation(projects.core.common.kotlin)
    implementation(projects.core.model)

    implementation(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)
}
