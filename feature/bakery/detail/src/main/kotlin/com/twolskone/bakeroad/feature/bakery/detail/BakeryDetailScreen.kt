package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.twolskone.bakeroad.core.designsystem.component.check.CheckSize
import com.twolskone.bakeroad.core.designsystem.component.check.SingleCheck
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadSheet
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryImagePager
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.component.review
import kotlinx.collections.immutable.persistentListOf
import timber.log.Timber

private val TopAppBarHeight = 56.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BakeryDetailScreen(
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val maxTopPadding = /*statusBarHeight + */TopAppBarHeight
    val headerWidthPx = windowInfo.containerSize.width
    val headerHeightPx = headerWidthPx / 3 * 2 + (with(density) { statusBarHeight.roundToPx() })
    var selectedTabIndex by remember { mutableIntStateOf(0) }   // TODO. test
    val listState = rememberLazyListState()
    val scrollIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val scrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val topPadding by remember {
        derivedStateOf {
            when {
                scrollIndex < 1 -> 0.dp

                scrollIndex == 1 -> {
                    val factor = scrollOffset.coerceIn(0, 700) / 700f
                    (factor * maxTopPadding)
                }

                else -> maxTopPadding
            }
        }
    }
    val topBarColorTransition by remember {
        derivedStateOf {
            val transition = when {
                scrollIndex == 0 && scrollOffset < headerHeightPx / 2 -> 0f
                scrollIndex == 0 -> (scrollOffset / headerHeightPx.toFloat()).coerceIn(0f, 1f)
                else -> 1f
            }
            transition.coerceIn(0f, 1f)
        }
    }
    val topBarColor by animateColorAsState(
        targetValue = BakeRoadTheme.colorScheme.White.copy(alpha = topBarColorTransition),
        label = "TopBarColorAnimation"
    )
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(topBarColor) {
        Timber.e("topBarColor : $topBarColor")
    }

    LaunchedEffect(topBarColorTransition) {
        Timber.e("topBarColorTransition : $topBarColorTransition")
    }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.Gray50)
                .padding(top = topPadding)
                .systemBarsPadding(),
            state = listState
        ) {
            item(contentType = "bakeryImagePager") {
                BakeryImagePager(
                    modifier = Modifier.fillMaxWidth(),
                    imageList = listOf("")
                )
            }
            item(contentType = "bakeryInfo") {
                BakeryInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            stickyHeader("tabs") {
                BakeRoadScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            ambientColor = BakeRoadTheme.colorScheme.Gray500,
                            spotColor = BakeRoadTheme.colorScheme.Gray500
                        ),
                    containerColor = BakeRoadTheme.colorScheme.White,
                    contentColor = BakeRoadTheme.colorScheme.Gray990,
                    edgePadding = 16.dp,
                    selectedTabIndex = selectedTabIndex
                ) {
                    BakeRoadTab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text(text = "홈") }
                    )
                    BakeRoadTab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text(text = "메뉴") }
                    )
                    BakeRoadTab(
                        selected = selectedTabIndex == 2,
                        onClick = { selectedTabIndex = 2 },
                        text = { Text(text = "리뷰") }
                    )
                    BakeRoadTab(
                        selected = selectedTabIndex == 3,
                        onClick = { selectedTabIndex = 3 },
                        text = { Text(text = "근처 관광지") }
                    )
                }
            }
//            home()
            review()
        }
        BakeRoadTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            containerColor = topBarColor,
            iconContentColor = BakeRoadTheme.colorScheme.Black,
            leftActions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                        .singleClickable {}
                        .padding(6.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            },
            title = {
                if (topBarColorTransition == 1f) {
                    Text(text = "서라당")
                }
            },
            rightActions = {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                            .singleClickable {}
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_share),
                            contentDescription = "Back"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                            .singleClickable {}
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_heart_stroke),
                            contentDescription = "Back"
                        )
                    }
                }
            }
        )
        if (showBottomSheet) {
            BakeRoadSheet(
                modifier = Modifier.fillMaxWidth(),
                buttonType = PopupButton.SHORT,
                title = stringResource(id = R.string.feature_bakery_detail_title_sort_order),
                primaryText = stringResource(id = R.string.feature_bakery_detail_button_sort),
                userActionContent = {
                    SingleCheck(
                        modifier = Modifier.fillMaxWidth(),
                        size = CheckSize.NORMAL,
                        selectedOption = "좋아요 순",
                        optionList = persistentListOf("좋아요 순", "최신 작성 순", "높은 평가 순", "낮은 평가 순"),
                        onCheck = {}
                    )
                },
                onDismissRequest = {
                    showBottomSheet = false
                },
                onPrimaryAction = {},
                onSecondaryAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun BakeryDetailScreenPreview() {
    BakeRoadTheme {
        BakeryDetailScreen()
    }
}