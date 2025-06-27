package com.twolskone.bakeroad.core.designsystem.component.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonVariant
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.coroutines.launch

private val SheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
private val SheetPadding = 16.dp

enum class SheetButton {
    SHORT, LONG
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BakeRoadSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    buttonType: SheetButton,
    title: String,
    content: String,
    primaryText: String,
    secondaryText: String,
    userActionContent: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = SheetShape,
        containerColor = BakeRoadTheme.colorScheme.White,
        contentColor = BakeRoadTheme.colorScheme.Gray990,
        tonalElevation = 2.dp,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = SheetPadding,
                    start = SheetPadding,
                    end = SheetPadding
                )
        ) {
            // Title.
            Text(
                text = title,
                style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Gray990)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Content.
            Text(
                text = content,
                style = BakeRoadTheme.typography.bodyXSmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // User action.
            if (userActionContent != null) {
                userActionContent()
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Buttons.
            BakeRoadSheetButtons(
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
internal fun BakeRoadSheetButtons(
    buttonType: SheetButton,
    primaryText: String,
    secondaryText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    when (buttonType) {
        SheetButton.SHORT -> {
            Row(modifier = Modifier.fillMaxWidth()) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.weight(1f),
                    role = OutlinedButtonVariant.SECONDARY,
                    size = ButtonSize.LARGE,
                    onClick = onSecondaryClick,
                    content = { Text(text = primaryText) }
                )
                BakeRoadSolidButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    role = SolidButtonVariant.PRIMARY,
                    size = ButtonSize.LARGE,
                    onClick = onPrimaryClick,
                    content = { Text(text = secondaryText) }
                )
            }
        }

        SheetButton.LONG -> {
            BakeRoadSolidButton(
                modifier = Modifier.fillMaxWidth(),
                role = SolidButtonVariant.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = onPrimaryClick,
                content = { Text(text = primaryText) }
            )
            BakeRoadOutlinedButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                role = OutlinedButtonVariant.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = onSecondaryClick,
                content = { Text(text = secondaryText) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BakeRoadSheetPreview() {
    BakeRoadTheme {
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()
        var showSheet by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BakeRoadSolidButton(
                modifier = Modifier.width(200.dp),
                onClick = { scope.launch { showSheet = true } },
                role = SolidButtonVariant.PRIMARY,
                size = ButtonSize.LARGE
            ) {
                Text(text = "눌러봐")
            }

            if (showSheet) {
                BakeRoadSheet(
                    buttonType = SheetButton.LONG,
                    title = "제목",
                    content = "내용",
                    primaryText = "권장행동",
                    secondaryText = "행동",
                    userActionContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(color = BakeRoadTheme.colorScheme.Gray50)
                        )
                    },
                    onDismissRequest = { showSheet = false },
                    onPrimaryAction = {},
                    onSecondaryAction = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showSheet = false
                            }
                        }
                    }
                )
            }
        }
    }
}