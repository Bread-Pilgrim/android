import com.twolskone.bakeroad.BakeRoadBuildType

plugins {
    alias(libs.plugins.bakeroad.android.application)
    alias(libs.plugins.bakeroad.android.application.compose)
    alias(libs.plugins.bakeroad.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.twolskone.bakeroad"

    defaultConfig {
        applicationId = "com.twolskone.bakeroad"
        versionCode = 1
        versionName = "1.0.0"   // X.Y.Z; X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = BakeRoadBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            applicationIdSuffix = BakeRoadBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // TODO. To publish on the Play store a private signing key is required.
            signingConfig = signingConfigs.named("debug").get()
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.intro)
    implementation(projects.feature.onboard)
    implementation(projects.feature.home)
    implementation(projects.feature.search)
    implementation(projects.feature.mybakery)
    implementation(projects.feature.mypage)
    implementation(projects.feature.bakery.list)
    implementation(projects.feature.bakery.detail)
    implementation(projects.feature.review.write)
    implementation(projects.feature.review.myreviews)
    implementation(projects.feature.settings)
    implementation(projects.feature.report)

    implementation(projects.core.common.kotlin)
    implementation(projects.core.common.android)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.remote)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.navigator)
    implementation(projects.core.model)
    implementation(projects.core.exception)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.timber)
    implementation(libs.kakao.auth) // 카카오 로그인

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}