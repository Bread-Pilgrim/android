package com.twolskone.bakeroad.feature.review.write.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.WriteReviewNavigator
import com.twolskone.bakeroad.feature.review.write.WriteReviewActivity
import javax.inject.Inject

internal class WriteReviewNavigatorImpl @Inject constructor() : WriteReviewNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, WriteReviewActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, WriteReviewActivity::class.java).intentBuilder())
    }
}