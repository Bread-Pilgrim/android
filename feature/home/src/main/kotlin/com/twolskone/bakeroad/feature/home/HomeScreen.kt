package com.twolskone.bakeroad.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadLineChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.skeleton.ChipsSkeleton
import com.twolskone.bakeroad.core.designsystem.component.skeleton.LineChipsSkeleton
import com.twolskone.bakeroad.core.designsystem.component.skeleton.SimpleBakeriesSkeleton
import com.twolskone.bakeroad.core.designsystem.component.skeleton.TitleSkeleton
import com.twolskone.bakeroad.core.designsystem.component.skeleton.TourAreasSkeleton
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.core.ui.TourAreaCard
import com.twolskone.bakeroad.feature.home.component.RecommendBakeryCard
import com.twolskone.bakeroad.feature.home.component.Title
import com.twolskone.bakeroad.feature.home.mvi.HomeState

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: HomeState,
    onAreaSelect: (Boolean, Int) -> Unit,
    onTourCategorySelect: (Boolean, TourAreaCategory) -> Unit,
    onSeeAllBakeriesClick: (BakeryType) -> Unit,
    onBakeryClick: (RecommendBakery) -> Unit,
    onEditPreferenceClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White),
        contentPadding = PaddingValues(bottom = padding.calculateBottomPadding())
    ) {
        item {
            BakeRoadTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                leftActions = {
                    Image(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.feature_home_ic_logo),
                        contentDescription = "Logo"
                    )
                },
                rightActions = {
                    BakeRoadTextButton(
                        modifier = Modifier.padding(end = 4.dp),
                        style = TextButtonStyle.ASSISTIVE,
                        size = TextButtonSize.SMALL,
                        content = { Text(text = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_button_preference_change)) },
                        onClick = onEditPreferenceClick
                    )
                }
            )
        }
        // 지역 필터
        stickyHeader {
            if (state.loadingState.areaLoading) {
                LineChipsSkeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 30.dp)
                        .padding(horizontal = 16.dp)
                )
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = BakeRoadTheme.colorScheme.White)
                            .padding(top = 10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(
                            items = state.areaList,
                            key = { area -> area.code }
                        ) { area ->
                            BakeRoadLineChip(
                                selected = state.selectedAreaCodes.contains(area.code),
                                selectInterval = 0,
                                onSelectedChange = { onAreaSelect(it, area.code) },
                                label = { Text(text = area.name) }
                            )
                        }
                    }
                    // Gradient. (White <- Transparent)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(
                                Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.2f to Color.White,
                                        0.4f to Color.White.copy(alpha = 0.8f),
                                        0.6f to Color.White.copy(alpha = 0.5f),
                                        0.8f to Color.White.copy(alpha = 0.2f),
                                        1.0f to Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }
        }
        // 내 취향 추천 빵집 제목
        item(contentType = "titleWithSeeAll") {
            if (state.loadingState.preferenceBakeryLoading) {
                TitleSkeleton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    titleWidth = 160.dp
                )
            } else {
                Title(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.feature_home_title_my_preference_bakery),
                    onSeeAllClick = { onSeeAllBakeriesClick(BakeryType.PREFERENCE) }
                )
            }
        }
        // 내 취향 추천 빵집 목록
        item(contentType = "bakeries") {
            if (state.loadingState.preferenceBakeryLoading) {
                SimpleBakeriesSkeleton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth()
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = state.preferenceBakeryList,
                        key = { bakery -> bakery.id }
                    ) {
                        RecommendBakeryCard(
                            bakery = it,
                            onCardClick = { bakery -> onBakeryClick(bakery) }
                        )
                    }
                }
            }
        }
        // Hot한 빵집 제목
        item(contentType = "titleWithSeeAll") {
            if (state.loadingState.hotBakeryLoading) {
                TitleSkeleton(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    titleWidth = 154.dp
                )
            } else {
                Title(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.feature_home_title_hot_bakery),
                    onSeeAllClick = { onSeeAllBakeriesClick(BakeryType.HOT) }
                )
            }
        }
        // Hot한 빵집 목록
        item(contentType = "bakeries") {
            if (state.loadingState.hotBakeryLoading) {
                SimpleBakeriesSkeleton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth()
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = state.hotBakeryList,
                        key = { bakery -> bakery.id }
                    ) {
                        RecommendBakeryCard(
                            bakery = it,
                            onCardClick = { bakery -> onBakeryClick(bakery) }
                        )
                    }
                }
            }
        }
        // 주변 추천 관광지 제목
        item {
            if (state.loadingState.tourAreaLoading) {
                TitleSkeleton(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    titleWidth = 190.dp,
                    skipButton = true
                )
            } else {
                Title(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.feature_home_title_tour_area)
                )
            }
        }
        // 관광지 카테고리 필터
        item {
            if (state.loadingState.tourAreaLoading) {
                ChipsSkeleton(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 2.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 2.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(
                        items = TourAreaCategory.entries,
                        key = { category -> category.code }
                    ) { category ->
                        BakeRoadChip(
                            selected = state.selectedTourAreaCategories.contains(category),
                            color = ChipColor.SUB,
                            size = ChipSize.LARGE,
                            selectInterval = 0,
                            onSelectedChange = { onTourCategorySelect(it, category) },
                            label = { Text(text = category.toLabel()) }
                        )
                    }
                }
            }
        }
        // 주변 추천 관광지 목록
        if (state.loadingState.tourAreaLoading) {
            item {
                TourAreasSkeleton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth()
                )
            }
        } else {
            items(
                items = state.tourAreaList,
                key = { tourArea -> "${tourArea.type}_${tourArea.title}_${tourArea.mapX}_${tourArea.mapY}" }
            ) {
                TourAreaCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    tourArea = it
                )
            }
        }
    }
}

@Composable
private fun TourAreaCategory.toLabel() =
    when (this) {
        TourAreaCategory.NATURE -> stringResource(id = R.string.feature_home_label_tour_area_category_nature)
        TourAreaCategory.HUMANITIES -> stringResource(id = R.string.feature_home_label_tour_area_category_humanities)
        TourAreaCategory.LEISURE -> stringResource(id = R.string.feature_home_label_tour_area_category_leisure)
        TourAreaCategory.SHOPPING -> stringResource(id = R.string.feature_home_label_tour_area_category_shopping)
        TourAreaCategory.RECOMMENDED_COURSE -> stringResource(id = R.string.feature_home_label_tour_area_category_recommend_course)
    }

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun HomeScreenPreview() {
    BakeRoadTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            padding = PaddingValues(0.dp),
            state = HomeState(),
            onAreaSelect = { _, _ -> },
            onTourCategorySelect = { _, _ -> },
            onSeeAllBakeriesClick = {},
            onBakeryClick = {},
            onEditPreferenceClick = {}
        )
    }
}