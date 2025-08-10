package com.twolskone.bakeroad.core.common.android.util

import android.content.Context
import android.os.Build

object DeviceUtil {

    val manufacturer: String = Build.MANUFACTURER
    val model: String = Build.MODEL
    val osVersion: String = Build.VERSION.RELEASE

    val Context.appVersion: String
        get() = runCatching {
            packageManager.getPackageInfo(packageName, 0).versionName.orEmpty()
        }.getOrDefault("")
    val Context.appVersionCode: String
        get() = runCatching {
            packageManager.getPackageInfo(packageName, 0).longVersionCode.toString()
        }.getOrDefault("")
}