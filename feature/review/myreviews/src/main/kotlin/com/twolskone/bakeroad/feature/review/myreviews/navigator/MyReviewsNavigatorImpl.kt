package com.twolskone.bakeroad.feature.review.myreviews.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.MyReviewsNavigator
import com.twolskone.bakeroad.feature.review.myreviews.MyReviewsActivity
import javax.inject.Inject

internal class MyReviewsNavigatorImpl @Inject constructor() : MyReviewsNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, MyReviewsActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, MyReviewsActivity::class.java).intentBuilder())
    }
}