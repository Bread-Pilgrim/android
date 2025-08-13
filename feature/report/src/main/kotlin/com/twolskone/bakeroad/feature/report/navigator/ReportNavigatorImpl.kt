package com.twolskone.bakeroad.feature.report.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.ReportNavigator
import com.twolskone.bakeroad.feature.report.ReportActivity
import javax.inject.Inject

internal class ReportNavigatorImpl @Inject constructor() : ReportNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, ReportActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, ReportActivity::class.java).intentBuilder())
    }
}