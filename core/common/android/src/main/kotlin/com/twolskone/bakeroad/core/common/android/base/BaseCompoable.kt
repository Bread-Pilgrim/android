package com.twolskone.bakeroad.core.common.android.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.twolskone.bakeroad.core.designsystem.component.snackbar.BakeRoadSnackbarHost
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <US : BaseUiState, I : BaseUiIntent, SE : BaseUiSideEffect> BaseComposable(
    baseViewModel: BaseViewModel<US, I, SE>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarTrigger: Pair<SnackbarState, Long>? by remember { mutableStateOf(null) }

    LaunchedEffect(baseViewModel) {
        baseViewModel.snackbarEffect.collectLatest { state ->
            snackbarTrigger = state to System.currentTimeMillis()
            snackbarHostState.showSnackbar(
                message = state.messageRes?.let { context.getString(it) } ?: state.message,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    content()

    snackbarTrigger?.let { (state, timeMillis) ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.BottomCenter
        ) {
            BakeRoadSnackbarHost(
                snackbarHostState = snackbarHostState,
                type = state.type,
                durationMills = state.duration
            )
        }
    }
}