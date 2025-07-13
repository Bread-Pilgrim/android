package com.twolskone.bakeroad.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.MainActivity
import com.twolskone.bakeroad.core.navigator.MainNavigator
import javax.inject.Inject

internal class MainNavigatorImpl @Inject constructor() : MainNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, MainActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, MainActivity::class.java).intentBuilder())
    }
}