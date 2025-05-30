package com.twolskone.bakeload

enum class BakeLoadBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}