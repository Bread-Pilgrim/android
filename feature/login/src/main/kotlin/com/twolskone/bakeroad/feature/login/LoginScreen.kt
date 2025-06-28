package com.twolskone.bakeroad.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadSheet
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.designsystem.component.snackbar.BakeRoadSnackbarBox
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(onKakaoLoginClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

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
                buttonType = PopupButton.LONG,
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
                onDismissRequest = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showSheet = false
                        }
                    }
                },
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

        BakeRoadSnackbarBox(
            snackbarHostState = snackbarHostState,
            type = SnackbarType.SUCCESS,
            message = "메세지를 내용을 입력하세요."
        )
//        Button(
//            onClick = { onKakaoLoginClick() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .navigationBarsPadding()
//                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
//                .height(60.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonColors(
//                containerColor = Color(0xFFFEE500),
//                contentColor = MaterialTheme.colorScheme.onBackground,
//                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
//            ),
//        ) {
//            Box { Text(text = "카카오 로그인") }
    }
}