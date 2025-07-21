package com.twolskone.bakeroad.feature.bakery.detail.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.feature.bakery.detail.BakeryDetailActivity
import javax.inject.Inject

class BakeryDetailNavigatorImpl @Inject constructor() : BakeryDetailNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, BakeryDetailActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, BakeryDetailActivity::class.java).intentBuilder())
    }
}