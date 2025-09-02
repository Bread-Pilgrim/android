package com.twolskone.bakeroad.feature.intro.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.IntroNavigator
import com.twolskone.bakeroad.feature.intro.IntroActivity
import javax.inject.Inject

internal class IntroNavigatorImpl @Inject constructor() : IntroNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, IntroActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, IntroActivity::class.java).intentBuilder())
    }
}