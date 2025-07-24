package com.twolskone.bakeroad.core.designsystem.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.R
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

@Composable
private fun BakeRoadCheckButton(
    role: Role,
    size: CheckButtonSize,
    checked: Boolean,
    enabled: Boolean = true,
    onCheck: () -> Unit,
    option: String
) {
    val checkColor = (if (checked) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray200).copy(
        alpha = if (enabled) 1f else 0.43f
    )
    val textStyle = BakeRoadTheme.typography.bodySmallMedium.copy(
        color = (if (checked) BakeRoadTheme.colorScheme.Gray990 else BakeRoadTheme.colorScheme.Gray200).copy(
            alpha = if (enabled) 1f else 0.43f
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = checked,
                onClick = onCheck,
                role = role,
                interactionSource = null,
                indication = null
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size.iconSize),
            imageVector = ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_check),
            contentDescription = "Check",
            tint = checkColor
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = option,
            style = textStyle
        )
    }
}

enum class CheckButtonSize {
    NORMAL, SMALL;

    val iconSize: Dp
        @Composable
        get() = when (this) {
            NORMAL -> 24.dp
            SMALL -> 20.dp
        }
}