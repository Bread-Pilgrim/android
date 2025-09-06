package com.twolskone.bakeroad.feature.review.write

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.MainNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.core.navigator.util.KEY_HOME_REFRESH
import com.twolskone.bakeroad.feature.review.write.navigation.WriteBakeryReviewNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class WriteBakeryReviewActivity : ComponentActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    private val viewModel: WriteReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                WriteBakeryReviewNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    navController = rememberNavController(),
                    finish = { finish() },
                    setResult = { code, finish ->
                        if (viewModel.state.value.achievedBadgeList.isNotEmpty()) {
                            val intent = Intent().apply { putExtra(KEY_BADGE_ACHIEVED, ArrayList(viewModel.state.value.achievedBadgeList)) }
                            setResult(code, intent)
                        } else {
                            setResult(code)
                        }
                        if (finish) finish()
                    },
                    goHome = {
                        mainNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = true,
                            intentBuilder = {
                                putExtra(KEY_HOME_REFRESH, true)
                                if (viewModel.state.value.achievedBadgeList.isNotEmpty()) {
                                    putExtra(KEY_BADGE_ACHIEVED, ArrayList(viewModel.state.value.achievedBadgeList))
                                }
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            }
                        )
                    }
                )
            }
        }
    }
}