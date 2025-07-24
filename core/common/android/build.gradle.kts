plugins {
    alias(libs.plugins.bakeroad.android.library)
}

android {
    namespace = "com.twolskone.bakeroad.core.common.android"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.coroutines.android)
}