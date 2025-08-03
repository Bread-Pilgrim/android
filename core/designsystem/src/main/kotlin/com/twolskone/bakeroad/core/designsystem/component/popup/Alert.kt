package com.twolskone.bakeroad.core.designsystem.component.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val WindowHorizontalPadding = 20.dp
private val AlertShape = RoundedCornerShape(20.dp)
private val AlertPadding = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BakeRoadAlert(
    modifier: Modifier = Modifier,
    buttonType: PopupButton,
    title: String = "",
    content: String = "",
    primaryText: String,
    secondaryText: String,
    onDismissRequest: () -> Unit,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit
) {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val width = remember { with(density) { windowInfo.containerSize.width.toDp() - (WindowHorizontalPadding * 2) } }

    BasicAlertDialog(
        modifier = modifier
            .width(width)
            .background(color = BakeRoadTheme.colorScheme.White, shape = AlertShape)
            .padding(AlertPadding),
        onDismissRequest = onDismissRequest,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = title,
                style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Gray990)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
            )
            Spacer(modifier = Modifier.height(20.dp))
            BakeRoadPopupButtons(
                buttonType = buttonType,
                primaryText = primaryText,
                secondaryText = secondaryText,
                onPrimaryClick = onPrimaryAction,
                onSecondaryClick = onSecondaryAction
            )
        }
    }
}

@Composable
private fun BakeRoadPopupButtons(
    buttonType: PopupButton,
    primaryText: String,
    secondaryText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    when (buttonType) {
        PopupButton.SHORT -> {
            Row(modifier = Modifier.fillMaxWidth()) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.weight(1f),
                    role = OutlinedButtonRole.SECONDARY,
                    size = ButtonSize.LARGE,
                    onClick = onSecondaryClick,
                    content = { Text(text = secondaryText) }
                )
                BakeRoadSolidButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    role = SolidButtonRole.PRIMARY,
                    size = ButtonSize.LARGE,
                    onClick = onPrimaryClick,
                    content = { Text(text = primaryText) }
                )
            }
        }

        PopupButton.LONG -> {
            BakeRoadSolidButton(
                modifier = Modifier.fillMaxWidth(),
                role = SolidButtonRole.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = onPrimaryClick,
                content = { Text(text = primaryText) }
            )
            BakeRoadOutlinedButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                role = OutlinedButtonRole.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = onSecondaryClick,
                content = { Text(text = secondaryText) }
            )
        }
    }
}

@Preview
@Composable
private fun BakeRoadAlertPreview() {
    BakeRoadTheme {
        BakeRoadAlert(
            buttonType = PopupButton.SHORT,
            title = "제목",
            content = "내용",
            primaryText = "권장행동",
            secondaryText = "행동",
            onDismissRequest = {},
            onPrimaryAction = {},
            onSecondaryAction = {}
        )
    }
}