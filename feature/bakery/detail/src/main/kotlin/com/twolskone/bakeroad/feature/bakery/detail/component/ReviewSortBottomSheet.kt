package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadRadioButtonGroup
import com.twolskone.bakeroad.core.designsystem.component.button.RadioButtonSize
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadSheet
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.feature.bakery.detail.R
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReviewSortBottomSheet(
    onDismissRequest: () -> Unit,
    sort: ReviewSortType,
    onSortSelect: (ReviewSortType) -> Unit,
    onCancel: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var selectedSort by remember { mutableStateOf(sort) }

    BakeRoadSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        buttonType = PopupButton.SHORT,
        title = stringResource(id = R.string.feature_bakery_detail_title_sort_order),
        primaryText = stringResource(id = R.string.feature_bakery_detail_button_sort),
        userActionContent = {
            BakeRoadRadioButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                radioButtonSize = RadioButtonSize.NORMAL,
                selectedIndex = selectedSort.ordinal,
                optionList = ReviewSortType.entries.map { it.label }.toImmutableList(),
                onOptionSelect = { selectedSort = ReviewSortType.entries[it] },
                verticalPadding = 8.dp,
                text = { index ->
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = ReviewSortType.entries[index].label,
                        style = BakeRoadTheme.typography.bodySmallMedium.copy(
                            color = if (selectedSort.ordinal == index) {
                                BakeRoadTheme.colorScheme.Gray990
                            } else {
                                BakeRoadTheme.colorScheme.Gray200
                            }
                        )
                    )
                }
            )
        },
        onDismissRequest = onDismissRequest,
        onPrimaryAction = {
            coroutineScope.launch { sheetState.hide() }
            onSortSelect(selectedSort)
        },
        onSecondaryAction = {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                onCancel()
            }
        }
    )
}

internal val ReviewSortType.label: String
    @Composable
    get() = when (this) {
        ReviewSortType.LIKE_COUNT_DESC -> stringResource(id = R.string.feature_bakery_detail_label_sort_like_count)
        ReviewSortType.CREATED_AT_DESC -> stringResource(id = R.string.feature_bakery_detail_label_sort_created_at)
        ReviewSortType.RATING_DESC -> stringResource(id = R.string.feature_bakery_detail_label_sort_rating_desc)
        ReviewSortType.RATING_ASC -> stringResource(id = R.string.feature_bakery_detail_label_sort_rating_asc)
    }