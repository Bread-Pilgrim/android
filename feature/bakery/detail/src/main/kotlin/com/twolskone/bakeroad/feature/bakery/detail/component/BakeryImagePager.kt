package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.ui.BakeryOpenStatusChip

private val colors = listOf(Color.DarkGray, Color.LightGray)

@Composable
internal fun BakeryImagePager(
    modifier: Modifier = Modifier,
    imageList: List<String>
) {
    val imagePagerState = rememberPagerState(pageCount = { imageList.size })

    Box(modifier = modifier.aspectRatio(3f / 2f)) {
        HorizontalPager(
            state = imagePagerState
        ) { imagePage ->
            imageList.getOrNull(imagePage)?.let { image ->
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = image,
                    contentDescription = "Bakery",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail),
                    error = ColorPainter(colors[imagePage])
                )
            }
        }
        ImagePagerIndicator(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            pageCount = imageList.size,
            currentPageIndex = imagePagerState.currentPage
        )
        BakeryOpenStatusChip(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            openStatus = BakeryOpenStatus.OPEN
        )
    }
}

@Composable
private fun ImagePagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageIndex: Int,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = BakeRoadTheme.colorScheme.Gray800.copy(alpha = 0.6f))
            .padding(horizontal = 10.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentPageIndex + 1}/$pageCount",
            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray50)
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    BakeRoadTheme {
        BakeryImagePager(
            modifier = Modifier.fillMaxWidth(),
            imageList = listOf("", "")
        )
    }
}