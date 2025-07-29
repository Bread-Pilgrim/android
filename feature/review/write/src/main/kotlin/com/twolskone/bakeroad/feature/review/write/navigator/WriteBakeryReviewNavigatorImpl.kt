package com.twolskone.bakeroad.feature.review.write.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.WriteBakeryReviewNavigator
import com.twolskone.bakeroad.feature.review.write.WriteBakeryReviewActivity
import javax.inject.Inject

internal class WriteBakeryReviewNavigatorImpl @Inject constructor() : WriteBakeryReviewNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, WriteBakeryReviewActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, WriteBakeryReviewActivity::class.java).intentBuilder())
    }
}