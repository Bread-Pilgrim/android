package com.twolskone.bakeroad.feature.review.write.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewIntent

@Composable
internal fun MenuSelectionRoute(
    viewModel: WriteReviewViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MenuSelectionScreen(
        menuList = state.menuList,
        onMenuSelect = { menu, selected -> viewModel.intent(WriteReviewIntent.SelectMenu(menuId = menu.id, selected = selected)) },
        onAddMenuCount = { menu -> viewModel.intent(WriteReviewIntent.AddMenuCount(menuId = menu.id)) },
        onRemoveMenuCount = { menu -> viewModel.intent(WriteReviewIntent.RemoveMenuCount(menuId = menu.id)) },
        onNextClick = onNextClick,
        onBackClick = onBackClick
    )
}