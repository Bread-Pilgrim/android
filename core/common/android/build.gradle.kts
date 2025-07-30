plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.android.library.compose)
}

android {
    namespace = "com.twolskone.bakeroad.core.common.android"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.timber)
}