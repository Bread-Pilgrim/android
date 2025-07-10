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
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.Primary50
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.home.component.Title
import com.twolskone.bakeroad.feature.home.component.TourAreaCard

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.background(Color.White)) {
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
        stickyHeader {
            // TEST
            Column {
                // Filters.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(52.dp)
                        .background(color = Primary50)
                ) { }
                // Gradient. (White <- Transparent)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // 상단 10dp 정도만
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.White,
                                    0.7f to Color.White.copy(alpha = 0.6f),
                                    0.9f to Color.White.copy(alpha = 0.3f),
                                    1.0f to Color.Transparent
                                )
                            )
                        )
                )
            }
        }
        // 주변 추천 관광지 제목.
        item {
            Title(
                title = stringResource(id = R.string.feature_home_title_tour_area),
                onSeeAllClick = {}
            )
        }
        // 관광지 카테고리 필터.
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 14.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
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
        items(count = 3 /* test */) {
            TourAreaCard()
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