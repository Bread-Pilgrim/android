package com.twolskone.bakeroad

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.IntentCompat
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import com.twolskone.bakeroad.core.common.kotlin.extension.toIntOrZero
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.SystemBarColorTheme
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.navigator.BadgeListNavigator
import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.core.navigator.BakeryListNavigator
import com.twolskone.bakeroad.core.navigator.MyReviewsNavigator
import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import com.twolskone.bakeroad.core.navigator.ReportNavigator
import com.twolskone.bakeroad.core.navigator.SettingsNavigator
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.core.navigator.util.KEY_HOME_REFRESH
import com.twolskone.bakeroad.mvi.MainIntent
import com.twolskone.bakeroad.ui.BakeRoadApp
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
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
    lateinit var badgeListNavigator: BadgeListNavigator         // 받은 뱃지

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
                    navigateToBadgeList = {
                        badgeListNavigator.navigateFromActivity(
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

        handleDeepLink()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.i("xxx onNewIntent")
        when {
            // 홈 갱신
            intent.getBooleanExtra(KEY_HOME_REFRESH, false) -> {
                Timber.i("xxx onNewIntent // RefreshHome")
                viewModel.intent(MainIntent.RefreshHome)
            }
            // 빵집 공유
            intent.data?.getQueryParameter("bakery_id").toIntOrZero() > 0 -> {
                val bakeryId = intent.data?.getQueryParameter("bakery_id")?.toInt()
                val areaCode = intent.data?.getQueryParameter("areaCode")?.toIntOrNull() ?: EntireBusan
                Timber.i("xxx onNewIntent // NavigateToBakeryDetail(id: $bakeryId, areaCode: $areaCode)")
                bakeryDetailNavigator.navigateFromActivity(
                    activity = this,
                    withFinish = false,
                    intentBuilder = {
                        putExtra("bakeryId", bakeryId)
                        putExtra("areaCode", areaCode)
                    }
                )
            }
        }
        // 뱃지 획득
        (IntentCompat.getSerializableExtra(intent, KEY_BADGE_ACHIEVED, Serializable::class.java) as? ArrayList<*>)?.let { list ->
            val badges = list.filterIsInstance<Badge>()
            Timber.i("xxx onNewIntent // AchieveBadges($badges)")
            if (badges.isNotEmpty()) {
                viewModel.intent(MainIntent.AchieveBadges(badges = badges))
            }
        }
    }

    private fun handleDeepLink() {
        if (intent.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                // 빵집 공유
                uri.getQueryParameter("bakery_id")?.toIntOrNull()?.let { bakeryId ->
                    val areaCode = uri.getQueryParameter("area_code")?.toIntOrNull() ?: EntireBusan
                    bakeryDetailNavigator.navigateFromActivity(
                        activity = this,
                        withFinish = false,
                        intentBuilder = {
                            putExtra("bakeryId", bakeryId)
                            putExtra("areaCode", areaCode)
                        }
                    )
                }
            }
        }
    }
}
