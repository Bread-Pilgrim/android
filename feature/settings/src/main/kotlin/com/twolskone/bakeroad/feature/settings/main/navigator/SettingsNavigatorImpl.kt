package com.twolskone.bakeroad.feature.settings.main.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.SettingsNavigator
import com.twolskone.bakeroad.feature.settings.SettingsActivity
import javax.inject.Inject

internal class SettingsNavigatorImpl @Inject constructor() : SettingsNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, SettingsActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, SettingsActivity::class.java).intentBuilder())
    }
}