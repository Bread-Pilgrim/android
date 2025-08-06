package com.twolskone.bakeroad.feature.mybakery.component

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
import com.twolskone.bakeroad.core.model.type.BakerySortType
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BakerySortBottomSheet(
    sort: BakerySortType,
    onDismissRequest: () -> Unit,
    onSortSelect: (BakerySortType) -> Unit,
    onCancel: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var selectedSort by remember { mutableStateOf(sort) }

    BakeRoadSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        buttonType = PopupButton.SHORT,
        title = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_title_sort_order),
        primaryText = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_button_sort),
        userActionContent = {
            BakeRoadRadioButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                radioButtonSize = RadioButtonSize.NORMAL,
                selectedIndex = selectedSort.ordinal,
                optionList = BakerySortType.entries.map { it.label }.toImmutableList(),
                onOptionSelect = { selectedSort = BakerySortType.entries[it] },
                verticalPadding = 8.dp,
                text = { index ->
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = BakerySortType.entries[index].label,
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

internal val BakerySortType.label: String
    @Composable
    get() = when (this) {
        BakerySortType.CREATED_AT_DESC -> stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_sort_created_at)
        BakerySortType.REVIEW_COUNT_DESC -> stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_sort_review_desc)
        BakerySortType.AVG_RATING_DESC -> stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_bakery_sort_rating_desc)
        BakerySortType.AVG_RATING_ASC -> stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_bakery_sort_rating_asc)
        BakerySortType.NAME -> stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_sort_name)
    }