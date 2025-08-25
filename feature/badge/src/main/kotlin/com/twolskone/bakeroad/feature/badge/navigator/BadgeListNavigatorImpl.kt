package com.twolskone.bakeroad.feature.badge.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.BadgeListNavigator
import com.twolskone.bakeroad.feature.badge.BadgeListActivity
import javax.inject.Inject

internal class BadgeListNavigatorImpl @Inject constructor() : BadgeListNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, BadgeListActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, BadgeListActivity::class.java).intentBuilder())
    }
}