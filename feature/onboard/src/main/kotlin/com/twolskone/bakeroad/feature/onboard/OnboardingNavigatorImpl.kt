package com.twolskone.bakeroad.feature.onboard

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import javax.inject.Inject

internal class OnboardingNavigatorImpl @Inject constructor() : OnboardingNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, OnboardingActivity::class.java).intentBuilder())
        if (withFinish) {
            activity.finish()
        }
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, OnboardingActivity::class.java).intentBuilder())
    }
}