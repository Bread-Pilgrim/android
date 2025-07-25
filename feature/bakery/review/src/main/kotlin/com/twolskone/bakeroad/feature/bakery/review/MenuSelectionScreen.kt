package com.twolskone.bakeroad.feature.bakery.review

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

@Composable
internal fun MenuSelectionScreen() {

}

private const val MenuAnimationDuration = 50

@Composable
private fun MenuSelectionListItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    onAddCount: () -> Unit,
    onRemoveCount: () -> Unit
) {
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
            .singleClickable { onClick() }
            .background(color = containerColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BakeRoadChip(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .padding(end = 2.dp),
                selected = false,
                color = ChipColor.SUB,
                size = ChipSize.SMALL,
                label = { Text(text = stringResource(id = com.twolskone.bakeroad.core.ui.R.string.core_ui_label_signature_menu)) }
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "꿀고구마 휘낭시에",
                style = BakeRoadTheme.typography.bodySmallMedium.copy(color = contentColor)
            )
            Image(
                modifier = Modifier.padding(start = 6.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_review_ic_check),
                contentDescription = "Check"
            )
        }
        if (selected) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.feature_bakery_review_description_select_purchase_quantity),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = contentColor)
                )
                QuantityCounter(
                    count = 1,
                    onAdd = { },
                    onRemove = { }
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
    onRemove: () -> Unit,
    onAdd: () -> Unit
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
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_review_ic_minus),
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
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_review_ic_plus),
                contentDescription = "Add",
                tint = addIconColor
            )
        }
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
                selected = selected,
                onClick = { selected = !selected },
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