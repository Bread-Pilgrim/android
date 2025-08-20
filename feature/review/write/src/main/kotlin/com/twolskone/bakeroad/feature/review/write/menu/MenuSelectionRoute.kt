package com.twolskone.bakeroad.feature.review.write.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewIntent

@Composable
internal fun MenuSelectionRoute(
    viewModel: WriteReviewViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BaseComposable(baseViewModel = viewModel) {
        MenuSelectionScreen(
            menuList = state.menuList,
            onMenuSelect = { menu, selected -> viewModel.intent(WriteBakeryReviewIntent.SelectMenu(menuId = menu.id, selected = selected)) },
            onAddMenuCount = { menu -> viewModel.intent(WriteBakeryReviewIntent.AddMenuCount(menuId = menu.id)) },
            onRemoveMenuCount = { menu -> viewModel.intent(WriteBakeryReviewIntent.RemoveMenuCount(menuId = menu.id)) },
            onNextClick = onNextClick,
            onBackClick = onBackClick
        )
    }
}