package com.twolskone.bakeroad

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.core.navigator.BakeryListNavigator
import com.twolskone.bakeroad.core.navigator.MyReviewsNavigator
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import com.twolskone.bakeroad.core.navigator.ReportNavigator
import com.twolskone.bakeroad.core.navigator.SettingsNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_HOME_REFRESH
import com.twolskone.bakeroad.mvi.MainIntent
import com.twolskone.bakeroad.ui.BakeRoadApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bakeryListNavigator: BakeryListNavigator       // 빵집 목록

    @Inject
    lateinit var bakeryDetailNavigator: BakeryDetailNavigator   // 빵집 상세

    @Inject
    lateinit var onboardingNavigator: OnboardingNavigator       // 취향 설정

    @Inject
    lateinit var settingsNavigator: SettingsNavigator           // 설정

    @Inject
    lateinit var reportNavigator: ReportNavigator               // 빵말정산

    @Inject
    lateinit var myReviewsNavigator: MyReviewsNavigator         // 내가 쓴 리뷰

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarColorTheme(lightTheme = true)
            BakeRoadTheme {
                BakeRoadApp(
                    viewModel = viewModel,
                    mainEventBus = viewModel.mainEventBus,
                    navController = rememberNavController(),
                    navigateToBakeryList = { areaCodes, bakeryType, launcher ->
                        bakeryListNavigator.navigateFromLauncher(
                            activity = this,
                            launcher = launcher,
                            intentBuilder = {
                                putExtra("areaCodes", areaCodes)
                                putExtra("bakeryType", bakeryType)
                            }
                        )
                    },
                    navigateToBakeryDetail = { bakeryId, areaCode, launcher ->
                        bakeryDetailNavigator.navigateFromLauncher(
                            activity = this,
                            launcher = launcher,
                            intentBuilder = {
                                putExtra("bakeryId", bakeryId)
                                putExtra("areaCode", areaCode)
                            }
                        )
                    },
                    navigateToEditPreference = { launcher ->
                        onboardingNavigator.navigateFromLauncher(
                            activity = this,
                            intentBuilder = { putExtra("isEditPreference", true) },
                            launcher = launcher
                        )
                    },
                    navigateToSettings = {
                        settingsNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = false
                        )
                    },
                    navigateToReport = {
                        reportNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = false
                        )
                    },
                    navigateToMyReviews = {
                        myReviewsNavigator.navigateFromActivity(
                            activity = this,
                            withFinish = false
                        )
                    },
                    openBrowser = { url ->
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    },
                    finish = { finish() }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.i("xxx onNewIntent")
        when {
            intent.getBooleanExtra(KEY_HOME_REFRESH, false) -> {
                Timber.i("xxx onNewIntent // RefreshHome")
                viewModel.intent(MainIntent.RefreshHome)
            }
        }
    }
}