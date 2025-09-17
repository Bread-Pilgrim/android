package com.twolskone.bakeroad.feature.photoviewer.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.twolskone.bakeroad.core.navigator.PhotoViewerNavigator
import com.twolskone.bakeroad.feature.photoviewer.PhotoViewerActivity
import javax.inject.Inject

internal class PhotoViewerNavigatorImpl @Inject constructor() : PhotoViewerNavigator {

    override fun navigateFromActivity(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent
    ) {
        activity.startActivity(Intent(activity, PhotoViewerActivity::class.java).intentBuilder())
        if (withFinish) activity.finish()
    }

    override fun navigateFromLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>,
        intentBuilder: Intent.() -> Intent
    ) {
        launcher.launch(Intent(activity, PhotoViewerActivity::class.java).intentBuilder())
    }
}