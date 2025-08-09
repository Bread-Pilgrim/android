package com.twolskone.bakeroad.feature.review.write.menu

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.ReviewMenu
import com.twolskone.bakeroad.core.model.isOtherMenu
import com.twolskone.bakeroad.feature.review.write.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MenuSelectionScreen(
    modifier: Modifier = Modifier,
    menuList: ImmutableList<ReviewMenu>,
    onMenuSelect: (ReviewMenu, Boolean) -> Unit,
    onAddMenuCount: (ReviewMenu) -> Unit,
    onRemoveMenuCount: (ReviewMenu) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val isMenusSelected = menuList.any { menu -> menu.count > 0 }

    Column(modifier = modifier.background(color = BakeRoadTheme.colorScheme.White)) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    onClick = onBackClick
                )
            },
            title = { Text(text = stringResource(id = R.string.feature_review_write_title_menu_selection)) },
            rightActions = {
                BakeRoadTextButton(
                    style = TextButtonStyle.ASSISTIVE,
                    size = TextButtonSize.MEDIUM,
                    enabled = isMenusSelected,
                    onClick = onNextClick,
                    content = { Text(text = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_button_next)) }
                )
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = menuList,
                key = { menu -> menu.id }
            ) { menu ->
                MenuSelectionListItem(
                    menu = menu,
                    onClick = onMenuSelect,
                    onAddCount = onAddMenuCount,
                    onRemoveCount = onRemoveMenuCount
                )
            }
        }
    }
}

private const val MenuAnimationDuration = 50

private val MenuSelectionListItemShape = RoundedCornerShape(12.dp)

@Composable
private fun MenuSelectionListItem(
    modifier: Modifier = Modifier,
    menu: ReviewMenu,
    onClick: (ReviewMenu, Boolean) -> Unit,
    onAddCount: (ReviewMenu) -> Unit,
    onRemoveCount: (ReviewMenu) -> Unit
) {
    val selected by remember(menu.count) { derivedStateOf { menu.count > 0 } }
    val containerColor by animateColorAsState(
        targetValue = if (selected) BakeRoadTheme.colorScheme.Secondary500 else BakeRoadTheme.colorScheme.Gray40,
        animationSpec = tween(durationMillis = MenuAnimationDuration)
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) BakeRoadTheme.colorScheme.White else BakeRoadTheme.colorScheme.Black,
        animationSpec = tween(durationMillis = MenuAnimationDuration)
    )
    Column(
        modifier = modifier
            .clip(MenuSelectionListItemShape)
            .singleClickable { onClick(menu, !selected) }
            .background(color = containerColor, shape = MenuSelectionListItemShape)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (menu.isSignature) {
                BakeRoadChip(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .padding(end = 2.dp),
                    selected = false,
                    color = ChipColor.SUB,
                    size = ChipSize.SMALL,
                    label = { Text(text = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_signature_menu)) }
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = if (menu.isOtherMenu()) stringResource(id = R.string.feature_review_write_button_other_menu) else menu.name,
                style = BakeRoadTheme.typography.bodySmallMedium.copy(color = contentColor)
            )
            Image(
                modifier = Modifier.padding(start = 6.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_check),
                contentDescription = "Check"
            )
        }
        if (selected && !menu.isOtherMenu()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.feature_review_write_description_select_purchase_quantity),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = contentColor)
                )
                QuantityCounter(
                    count = menu.count,
                    onAdd = { onAddCount(menu) },
                    onRemove = { onRemoveCount(menu) }
                )
            }
        }
    }
}

private val QuantityCounterHeight = 37.dp

private const val MinQuantityCount = 1
private const val MaxQuantityCount = 99

@Composable
private fun QuantityCounter(
    modifier: Modifier = Modifier,
    count: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    val removeIconColor by animateColorAsState(
        targetValue = if (count > MinQuantityCount) BakeRoadTheme.colorScheme.Black else BakeRoadTheme.colorScheme.Gray200,
        animationSpec = tween(durationMillis = MenuAnimationDuration)
    )
    val addIconColor by animateColorAsState(
        targetValue = if (count < MaxQuantityCount) BakeRoadTheme.colorScheme.Black else BakeRoadTheme.colorScheme.Gray200,
        animationSpec = tween(durationMillis = MenuAnimationDuration)
    )

    Row(
        modifier = modifier
            .height(QuantityCounterHeight)
            .background(color = BakeRoadTheme.colorScheme.White, shape = RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .singleClickable {
                    if (count > MinQuantityCount) {
                        onRemove()
                    }
                }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_minus),
                contentDescription = "Remove",
                tint = removeIconColor
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .widthIn(min = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                style = BakeRoadTheme.typography.bodySmallMedium
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .singleClickable {
                    if (count < MaxQuantityCount) {
                        onAdd()
                    }
                }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_plus),
                contentDescription = "Add",
                tint = addIconColor
            )
        }
    }
}

@Preview
@Composable
private fun MenuSelectionScreenPreview() {
    BakeRoadTheme {
        MenuSelectionScreen(
            modifier = Modifier.fillMaxSize(),
            menuList = persistentListOf(
                ReviewMenu(
                    id = 1,
                    name = "꿀고구마 휘낭시에 1",
                    isSignature = true,
                    count = 1
                ),
                ReviewMenu(
                    id = 1,
                    name = "꿀고구마 휘낭시에 2",
                    isSignature = true,
                    count = 0
                ),
                ReviewMenu(
                    id = 1,
                    name = "꿀고구마 휘낭시에 3",
                    isSignature = false,
                    count = 10
                ),
                ReviewMenu(
                    id = 1,
                    name = "꿀고구마 휘낭시에 4",
                    isSignature = false,
                    count = 0
                )
            ),
            onMenuSelect = { _, _ -> },
            onAddMenuCount = {},
            onRemoveMenuCount = {},
            onNextClick = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun MenuSelectionListItemPreview() {
    BakeRoadTheme {
        var selected by remember { mutableStateOf(true) }

        Box(modifier = Modifier.fillMaxSize()) {
            MenuSelectionListItem(
                modifier = Modifier.fillMaxWidth(),
                menu = ReviewMenu(
                    id = 1,
                    name = "꿀고구마 휘낭시에 3",
                    isSignature = false,
                    count = 10
                ),
                onClick = { _, isSelected -> selected = isSelected },
                onAddCount = {},
                onRemoveCount = {}
            )
        }
    }
}

@Preview
@Composable
private fun QuantityCounterPreview() {
    BakeRoadTheme {
        var count by remember { mutableIntStateOf(99) }

        QuantityCounter(
            count = count,
            onAdd = { count++ },
            onRemove = { count-- }
        )
    }
}