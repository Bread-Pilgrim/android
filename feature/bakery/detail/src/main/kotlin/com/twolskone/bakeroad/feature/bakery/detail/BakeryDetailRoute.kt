package com.twolskone.bakeroad.feature.bakery.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.IntentCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.core.common.android.extension.isEmpty
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.core.navigator.util.KEY_BAKERY_ID
import com.twolskone.bakeroad.core.navigator.util.KEY_BAKERY_LIKE
import com.twolskone.bakeroad.core.navigator.util.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.core.ui.popup.BadgeAchievedBottomSheet
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailSideEffect
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import java.io.Serializable
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel(),
    navigateToWriteBakeryReview: (Int, ActivityResultLauncher<Intent>) -> Unit,
    navigateToBadgeList: () -> Unit,
    setResult: (code: Int, intent: Intent?, withFinish: Boolean) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()
    val reviewTabState by viewModel.reviewTabState.collectAsStateWithLifecycle()
    val reviewSortState by viewModel.reviewSortState.collectAsStateWithLifecycle()
    val myReviewPagingItems = viewModel.myReviewPagingFlow.collectAsLazyPagingItems()
    val reviewPagingItems = viewModel.reviewPagingFlow.collectAsLazyPagingItems()
    var achievedBadges by remember { mutableStateOf<List<Badge>>(emptyList()) }
    val writeReviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.i("xxx writeReviewLauncher :: Completed write review")
            result.data?.let { intent ->
                // 뱃지 획득
                (IntentCompat.getSerializableExtra(intent, KEY_BADGE_ACHIEVED, Serializable::class.java) as? ArrayList<*>)?.let { list ->
                    achievedBadges = list.filterIsInstance<Badge>()
                }
            }
            viewModel.mainEvetBus.setHomeRefreshState(value = true) // 홈 갱신
            if (tabState == BakeryDetailTab.REVIEW) {
                when (reviewTabState) {
                    ReviewTab.ALL_REVIEW -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab = ReviewTab.MY_REVIEW))
                    ReviewTab.MY_REVIEW -> myReviewPagingItems.refresh()
                }
            } else {
                viewModel.intent(BakeryDetailIntent.SelectTab(tab = BakeryDetailTab.REVIEW))
                if (reviewTabState == ReviewTab.ALL_REVIEW) viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab = ReviewTab.MY_REVIEW))
            }
        } else {
            Timber.i("xxx writeReviewLauncher :: Canceled write review")
        }
    }

    BackHandler {
        setResult(
            RESULT_REFRESH_BAKERY_LIST,
            Intent().apply {
                putExtra(KEY_BAKERY_ID, viewModel.bakeryId)
                state.bakeryInfo?.let { putExtra(KEY_BAKERY_LIKE, it.isLike) }
            },
            true
        )
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                BakeryDetailSideEffect.NavigateToWriteBakeryReview -> navigateToWriteBakeryReview(viewModel.bakeryId, writeReviewLauncher)
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { reviewPagingItems.loadState.refresh }
            .filterIsInstance<LoadState.NotLoading>()
            .filter { !reviewPagingItems.isEmpty }
            .collect {
                reviewPagingItems.peek(0)?.let {
                    viewModel.intent(BakeryDetailIntent.UpdateReviewInfo(avgRating = it.avgRating, count = it.totalCount))
                }
            }
    }

    reviewPagingItems.ObserveError(viewModel)
    myReviewPagingItems.ObserveError(viewModel)

    BaseComposable(baseViewModel = viewModel) {
        BakeryDetailScreen(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            tabState = tabState,
            reviewTabState = reviewTabState,
            reviewSortState = reviewSortState,
            myReviewPagingItems = myReviewPagingItems,
            reviewPagingItems = reviewPagingItems,
            onTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectTab(tab)) },
            onReviewTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab)) },
            onReviewSortSelect = { sort -> viewModel.intent(BakeryDetailIntent.SelectReviewSort(sort)) },
            onWriteReviewClick = { viewModel.intent(BakeryDetailIntent.CheckReviewEligibility) },
            onBakeryLikeClick = { isLike -> viewModel.intent(BakeryDetailIntent.ClickBakeryLike(isLike = isLike)) },
            onReviewLikeClick = { id, isLike -> viewModel.intent(BakeryDetailIntent.ClickReviewLike(reviewId = id, isLike = isLike)) },
            onBackClick = {
                setResult(
                    RESULT_REFRESH_BAKERY_LIST,
                    Intent().apply {
                        putExtra(KEY_BAKERY_ID, viewModel.bakeryId)
                        state.bakeryInfo?.let { putExtra(KEY_BAKERY_LIKE, it.isLike) }
                    },
                    true
                )
            },
            onShareClick = {
                if (state.loadingState.bakeryDetailLoading) return@BakeryDetailScreen
                shareToKakaoTalk(
                    context = context,
                    bakeryId = viewModel.bakeryId,
                    areaCode = viewModel.areaCode,
                    state = state
                )
            }
        )

        if (achievedBadges.isNotEmpty()) {
            BadgeAchievedBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                badgeList = achievedBadges.toImmutableList(),
                onDismissRequest = { achievedBadges = emptyList() },
                onSeeBadgeClick = {
                    achievedBadges = emptyList()
                    navigateToBadgeList()
                }
            )
        }
    }
}

/**
 * 카카오톡 빵집 공유
 * @param bakeryId  빵집 ID (필수)
 * @param areaCode  지역코드 (필수)
 * @param state     빵집 상세 UiState
 */
private fun shareToKakaoTalk(
    context: Context,
    bakeryId: Int,
    areaCode: Int,
    state: BakeryDetailState
) {
    val feed = FeedTemplate(
        content = Content(
            title = state.bakeryInfo?.name.orEmpty(),
            description = state.bakeryInfo?.address.orEmpty(),
            imageUrl = state.bakeryImageList.firstOrNull().orEmpty(),
            link = Link(
                mobileWebUrl = "https://play.google.com/store/apps/details?id=${context.packageName}",
                webUrl = "https://play.google.com/store/apps/details?id=${context.packageName}"
            )
        ),
        buttons = listOf(
            Button(
                title = "자세히 보기",
                link = Link(
                    androidExecutionParams = mapOf(
                        "bakery_id" to bakeryId.toString(),
                        "area_code" to areaCode.toString()
                    )
                )
            )
        )
    )

    // 카카오톡 설치여부 확인
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        // 카카오톡으로 카카오톡 공유 가능
        ShareClient.instance.shareDefault(context, feed) { sharingResult, error ->
            if (error != null) {
                Timber.e("카카오톡 공유 실패", error)
            } else if (sharingResult != null) {
                Timber.d("카카오톡 공유 성공: ${sharingResult.intent}")
                context.startActivity(sharingResult.intent)

                // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Timber.w("Warning Msg: ${sharingResult.warningMsg}")
                Timber.w("Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    } else {
        // 카카오톡 미설치: 웹 공유 사용 권장
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(feed)

        // CustomTabs으로 웹 브라우저 열기

        // 1. CustomTabsServiceConnection 지원 브라우저 열기
        // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
        try {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        } catch (e: UnsupportedOperationException) {
            // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
        }

//        // 2. CustomTabsServiceConnection 미지원 브라우저 열기
//        // ex) 다음, 네이버 등
//        try {
//            KakaoCustomTabsClient.open(context, sharerUrl)
//        } catch (e: ActivityNotFoundException) {
//            // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
//        }
    }
}