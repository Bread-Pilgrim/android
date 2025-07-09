package com.twolskone.bakeroad.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.designsystem.theme.Primary50

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
        item {
            // TEST
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(1000.dp)
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                (0..100).forEach {
                    Text(
                        modifier = Modifier.padding(vertical = 50.dp),
                        text = "빵글 빵글 빵글 빵글 $it",
                        style = BakeRoadTheme.typography.headingMediumBold,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun HomeScreenPreview() {
    BakeRoadTheme {
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}