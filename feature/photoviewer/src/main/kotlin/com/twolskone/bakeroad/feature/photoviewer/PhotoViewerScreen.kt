package com.twolskone.bakeroad.feature.photoviewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.twolskone.bakeroad.core.analytics.LogComposeScreenEvent
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.photoviewer.mvi.PhotoViewerState
import kotlinx.collections.immutable.persistentListOf
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
internal fun PhotoViewerScreen(
    modifier: Modifier = Modifier,
    state: PhotoViewerState,
    pagerState: PagerState,
    onBackClick: () -> Unit
) {
    LogComposeScreenEvent(screen = "PhotoViewerScreen")

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(color = BakeRoadTheme.colorScheme.Black)
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            state.photoList.getOrNull(page)?.let {
                val zoomState = rememberZoomState()

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zoomable(zoomState = zoomState),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build(),
                    contentDescription = "Photo",
                    placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail),
                    onSuccess = { state ->
                        zoomState.setContentSize(state.painter.intrinsicSize)
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .align(Alignment.TopStart)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BakeRoadTopAppBarIcon(
                    modifier = Modifier,
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    backgroundColor = BakeRoadTheme.colorScheme.Black,
                    tint = BakeRoadTheme.colorScheme.White,
                    onClick = onBackClick
                )

                if (pagerState.pageCount > 1) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.Gray800.copy(alpha = 0.6f))
                            .padding(horizontal = 10.dp, vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                            style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray50)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 5.dp),
                text = state.title,
                style = BakeRoadTheme.typography.bodyMediumSemibold,
                color = BakeRoadTheme.colorScheme.White
            )
        }
    }
}

@Preview
@Composable
private fun PhotoViewerScreenPreview() {
    BakeRoadTheme {
        PhotoViewerScreen(
            state = PhotoViewerState(
                photoList = persistentListOf(
                    "https://img1.kakaocdn.net/cthumb/local/C544x408.q50/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flocal%2FkakaomapPhoto%2Freview%2F86f1d7ab1acf0c78d6faf0f0cfd3290e8795f817%3Foriginal",
                    "https://img1.kakaocdn.net/cthumb/local/C544x408.q50/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flocal%2FkakaomapPhoto%2Freview%2F86f1d7ab1acf0c78d6faf0f0cfd3290e8795f817%3Foriginal",
                    "https://img1.kakaocdn.net/cthumb/local/C544x408.q50/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flocal%2FkakaomapPhoto%2Freview%2F86f1d7ab1acf0c78d6faf0f0cfd3290e8795f817%3Foriginal"
                )
            ),
            pagerState = rememberPagerState { 3 },
            onBackClick = {}
        )
    }
}