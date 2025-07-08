package com.twolskone.bakeroad.core.navigator.base

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface Navigator {
    fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent = { this },
    )

    fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent = { this }
    )
}