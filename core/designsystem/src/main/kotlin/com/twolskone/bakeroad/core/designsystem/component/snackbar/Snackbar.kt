package com.twolskone.bakeroad.core.designsystem.component.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.R
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.coroutines.delay

private val SnackbarRadius = 12.dp

@Composable
fun BakeRoadSnackbarHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    theme: SnackbarTheme = SnackbarTheme.DARK,
    type: SnackbarType,
    containerColor: Color = theme.containerColor,
    contentColor: Color = theme.getContentColor(type),
    shape: Shape = RoundedCornerShape(SnackbarRadius),
    durationMills: Long?
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
        snackbar = {
            BakeRoadSnackbar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                snackbarData = it,
                containerColor = containerColor,
                contentColor = contentColor,
                shape = shape,
                type = type,
                durationMills = durationMills
            )
        }
    )
}

@Composable
private fun BakeRoadSnackbar(
    modifier: Modifier,
    snackbarData: SnackbarData,
    containerColor: Color,
    contentColor: Color,
    shape: Shape,
    type: SnackbarType,
    durationMills: Long?
) {
    LaunchedEffect(snackbarData) {
        if (durationMills != null) {
            delay(durationMills)
            snackbarData.dismiss()
        }
    }

    Snackbar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(imageVector = ImageVector.vectorResource(id = type.iconRes), contentDescription = "Snackbar")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = snackbarData.visuals.message,
                    style = BakeRoadTheme.typography.bodyXsmallMedium
                )
            }
        }
    )
}

enum class SnackbarTheme {
    DARK, WHITE;

    val containerColor: Color
        @Composable
        get() = when (this) {
            DARK -> BakeRoadTheme.colorScheme.Black
            WHITE -> BakeRoadTheme.colorScheme.White
        }

    @Composable
    fun getContentColor(type: SnackbarType): Color =
        when (this) {
            DARK -> if (type == SnackbarType.ERROR) BakeRoadTheme.colorScheme.Error500 else BakeRoadTheme.colorScheme.White
            WHITE -> if (type == SnackbarType.ERROR) BakeRoadTheme.colorScheme.Error500 else BakeRoadTheme.colorScheme.Success950
        }
}

@Immutable
data class SnackbarState(
    val type: SnackbarType,
    val message: String,
    val messageRes: Int? = null,
    val duration: Long = 2_000L
)

enum class SnackbarType(val iconRes: Int) {
    SUCCESS(iconRes = R.drawable.core_designsystem_ic_round_check),
    ERROR(iconRes = R.drawable.core_designsystem_ic_error)
}

@Preview
@Composable
private fun BakeRoadSnackbarBoxPreview() {
    BakeRoadTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.BottomCenter
        ) {
            BakeRoadSnackbarHost(
                snackbarHostState = remember { SnackbarHostState() },
                type = SnackbarType.SUCCESS,
                durationMills = 2_000L
            )
        }
    }
}