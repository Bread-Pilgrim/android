package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BakeryDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bakeryImageList = state.bakeryImageList,
        bakeryInfo = state.bakeryInfo,
        menuList = state.menuList
    )
}