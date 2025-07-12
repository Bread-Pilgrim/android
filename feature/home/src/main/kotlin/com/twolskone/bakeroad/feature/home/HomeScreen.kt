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
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.home.component.BakeryCard
import com.twolskone.bakeroad.feature.home.component.Title
import com.twolskone.bakeroad.feature.home.component.TourAreaCard

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.background(color = BakeRoadTheme.colorScheme.White)) {
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
                        onClick = {}
                    )
                }
            )
        }
        // 지역 필터.
        stickyHeader {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BakeRoadTheme.colorScheme.White),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(
                        items = TourAreaCategory.entries.toList(),
                        key = { category -> category.code }
                    ) { category ->
                        BakeRoadLineChip(
                            selected = false,
                            onSelectedChange = {},
                            label = { Text(text = category.toLabel()) }
                        )
                    }
                }
                // Gradient. (White <- Transparent)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // 상단 10dp 정도만
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.White,
                                    0.2f to Color.White.copy(alpha = 0.7f),
                                    0.5f to Color.White.copy(alpha = 0.4f),
                                    0.8f to Color.White.copy(alpha = 0.1f),
                                    1.0f to Color.Transparent
                                )
                            )
                        )
                )
            }
        }
        // 내 취향 추천 빵집 제목.
        item {
            Title(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.feature_home_title_my_preference_bakery),
                onSeeAllClick = {}
            )
        }
        // 내 취향 추천 빵집 목록.
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(count = 5) {
                    BakeryCard()
                }
            }
        }
        // Hot한 빵집 제목.
        item {
            Title(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.feature_home_title_hot_bakery),
                onSeeAllClick = {}
            )
        }
        // Hot한 빵집 목록.
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(count = 5) {
                    BakeryCard()
                }
            }
        }
        // 주변 추천 관광지 제목.
        item {
            Title(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                title = stringResource(id = R.string.feature_home_title_tour_area),
                onSeeAllClick = {}
            )
        }
        // 관광지 카테고리 필터.
        item {
            LazyRow(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                items(
                    items = TourAreaCategory.entries.toList(),
                    key = { category -> category.code }
                ) { category ->
                    BakeRoadChip(
                        selected = false,
                        color = ChipColor.SUB,
                        size = ChipSize.LARGE,
                        onSelectedChange = {},
                        label = { Text(text = category.toLabel()) }
                    )
                }
            }
        }
        // 주변 추천 관광지 목록.
        items(count = 10 /* test */) {
            TourAreaCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
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
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}