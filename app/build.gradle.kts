import com.twolskone.bakeload.BakeLoadBuildType

plugins {
    alias(libs.plugins.bakeload.android.application)
    alias(libs.plugins.bakeload.android.application.compose)
    alias(libs.plugins.bakeload.hilt)
}

android {
    namespace = "com.twolskone.bakeload"

    defaultConfig {
        applicationId = "com.twolskone.bakeload"
        versionCode = 1
        versionName = "1.0.0"   // X.Y.Z; X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = BakeLoadBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            applicationIdSuffix = BakeLoadBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}