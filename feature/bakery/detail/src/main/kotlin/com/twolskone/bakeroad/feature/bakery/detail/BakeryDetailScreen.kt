package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadScrollableTabRow
import com.twolskone.bakeroad.core.designsystem.component.tab.BakeRoadTab
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryImagePager
import com.twolskone.bakeroad.feature.bakery.detail.component.BakeryInfo

@Composable
internal fun BakeryDetailScreen(
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.Gray50)
        ) {
            item {
                BakeryImagePager(
                    modifier = Modifier.fillMaxWidth(),
                    imageList = listOf()
                )
            }
            item {
                BakeryInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            stickyHeader {
                BakeRoadScrollableTabRow(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = BakeRoadTheme.colorScheme.White,
                    contentColor = BakeRoadTheme.colorScheme.Gray990,
                    edgePadding = 16.dp,
                    selectedTabIndex = selectedTabIndex,
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
            item {
                Box(
                    modifier = Modifier
                        .height(1000.dp)
                        .background(Color.White)
                )
            }
        }

        BakeRoadTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            containerColor = Color.Transparent,
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
    }
}

@Preview
@Composable
private fun BakeryDetailScreenPreview() {
    BakeRoadTheme {
        BakeryDetailScreen()
    }
}