package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadSheetButtons
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.coroutines.launch

private val SheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
private val SheetHorizontalPadding = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalFestivalsSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//    buttonType: PopupButton,
//    content: String,
    primaryText: String = "",
    secondaryText: String = "",
//    userActionContent: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    onPrimaryAction: () -> Unit = {},
    onSecondaryAction: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.Transparent,
        contentColor = BakeRoadTheme.colorScheme.Gray990,
        tonalElevation = 2.dp,
        dragHandle = null
    ) {
        Column(modifier) {
            BakeRoadTextButton(
                modifier = Modifier.align(Alignment.End),
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                onClick = {},
                content = {
                    CompositionLocalProvider(
                        value = LocalContentColor provides BakeRoadTheme.colorScheme.White,
                        content = { Text(text = "닫기") }
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(color = BakeRoadTheme.colorScheme.White, shape = SheetShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = SheetHorizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "센텀 맥주 축제",
                    style = BakeRoadTheme.typography.headingSmallBold.copy(color = BakeRoadTheme.colorScheme.Gray990)
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "2025.07.06 ~ 2025.07.08",
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "부산 해운대구 수영강변대로 120 영화의전당",
                    style = BakeRoadTheme.typography.body2XsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                        .background(color = BakeRoadTheme.colorScheme.Gray50)
                )
                BakeRoadSheetButtons(
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    buttonType = PopupButton.SHORT,
                    primaryText = primaryText,
                    secondaryText = secondaryText,
                    onPrimaryClick = onPrimaryAction,
                    onSecondaryClick = onSecondaryAction
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LocalFestivalsSheetPreview() {
    BakeRoadTheme {
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()
        var showSheet by remember { mutableStateOf(false) }

        if (showSheet) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BakeRoadSolidButton(
                    modifier = Modifier.width(200.dp),
                    onClick = { scope.launch { showSheet = true } },
                    role = SolidButtonRole.PRIMARY,
                    size = ButtonSize.LARGE
                ) {
                    Text(text = "눌러봐")
                }
                LocalFestivalsSheet(
                    modifier = Modifier.wrapContentHeight(),
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