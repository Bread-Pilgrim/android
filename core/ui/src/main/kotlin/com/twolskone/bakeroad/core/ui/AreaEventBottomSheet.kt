package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.AreaEvent
import kotlinx.coroutines.launch

private val SheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
private val SheetHorizontalPadding = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaEventBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    areaEvent: AreaEvent,
    primaryText: String = stringResource(id = R.string.core_ui_button_see_details),
    secondaryText: String = stringResource(id = R.string.core_ui_button_dismiss_today),
    onDismissRequest: () -> Unit = {},
    onPrimaryAction: (link: String) -> Unit = {},
    onSecondaryAction: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

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
        Column(modifier = Modifier.fillMaxWidth()) {
            BakeRoadTextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                onClick = onDismissRequest,
                content = {
                    CompositionLocalProvider(
                        value = LocalContentColor provides BakeRoadTheme.colorScheme.White,
                        content = { Text(text = stringResource(id = R.string.core_ui_button_close)) }
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
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White)
                    .padding(horizontal = SheetHorizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = areaEvent.title,
                    style = BakeRoadTheme.typography.headingSmallBold,
                    color = BakeRoadTheme.colorScheme.Gray990
                )
                if (areaEvent.startDate.isNotBlank() && areaEvent.endDate.isNotBlank()) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "${areaEvent.startDate} ~ ${areaEvent.endDate}",
                        style = BakeRoadTheme.typography.body2XsmallMedium,
                        color = BakeRoadTheme.colorScheme.Gray800
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = areaEvent.address,
                    style = BakeRoadTheme.typography.body2XsmallMedium,
                    color = BakeRoadTheme.colorScheme.Gray800
                )
                if (areaEvent.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .aspectRatio(3f / 2f),
                        model = areaEvent.imageUrl,
                        contentDescription = "AreaEvent",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .aspectRatio(3f / 2f),
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail),
                        contentScale = ContentScale.Crop,
                        contentDescription = "AreaEventPlaceholder"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BakeRoadOutlinedButton(
                        modifier = Modifier.weight(1f),
                        role = OutlinedButtonRole.ASSISTIVE,
                        size = ButtonSize.LARGE,
                        onClick = {
                            coroutineScope.launch { sheetState.hide() }
                            onSecondaryAction()
                        },
                        content = { Text(text = secondaryText) }
                    )
                    BakeRoadSolidButton(
                        modifier = Modifier.weight(1f),
                        role = SolidButtonRole.PRIMARY,
                        size = ButtonSize.LARGE,
                        onClick = {
                            coroutineScope.launch { sheetState.hide() }
                            onPrimaryAction(areaEvent.link)
                        },
                        content = { Text(text = primaryText) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AreaEventBottomSheetPreview() {
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
                role = SolidButtonRole.PRIMARY,
                size = ButtonSize.LARGE
            ) {
                Text(text = "눌러봐")
            }
        }
        if (showSheet) {
            AreaEventBottomSheet(
                modifier = Modifier,
                areaEvent = AreaEvent(
                    title = "센텀 맥주 축제",
                    address = "부산 해운대구 수영강변대로 120 영화의전당",
                    startDate = "2025.07.06",
                    endDate = "2025.07.08",
                    mapX = 0f,
                    mapY = 0f,
                    imageUrl = "",
                    link = ""
                ),
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