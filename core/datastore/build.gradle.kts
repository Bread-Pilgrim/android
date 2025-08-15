plugins {
    alias(libs.plugins.bakeroad.android.library)
    alias(libs.plugins.bakeroad.hilt)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.twolskone.bakeroad.core.datastore"
}

// Setup protobuf configuration, generating lite Java and Kotlin classes.
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(projects.core.common.kotlin)
    implementation(projects.core.model)

    api(libs.androidx.datastore.preferences)
    api(libs.androidx.datastore)
    api(libs.protobuf.kotlin.lite)
    implementation(libs.timber)
    implementation(libs.gson)
}