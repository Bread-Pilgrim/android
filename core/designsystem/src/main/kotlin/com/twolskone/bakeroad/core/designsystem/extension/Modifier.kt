package com.twolskone.bakeroad.core.designsystem.extension

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val INTERVAL_SINGLE_CLICK = 300L

// None ripple single clickable.
@Composable
fun Modifier.noRippleSingleClickable(
    onClickLabel: String? = null,
    role: Role? = null,
    clickInterval: Long = INTERVAL_SINGLE_CLICK,
    onClick: () -> Unit
): Modifier =
    composed {
        singleClickable(
            indication = null,
            onClickLabel = onClickLabel,
            role = role,
            clickInterval = clickInterval,
            onClick = onClick
        )
    }

// Ripple single clickable.
@Composable
fun Modifier.singleClickable(
    scope: CoroutineScope = rememberCoroutineScope(),
    onClickLabel: String? = null,
    role: Role? = null,
    clickInterval: Long = INTERVAL_SINGLE_CLICK,
    onClick: () -> Unit
): Modifier =
    composed {
        val clickable = remember { mutableStateOf(true) }

        Modifier.clickable(
            enabled = clickable.value,
            onClickLabel = onClickLabel,
            role = role,
            onClick = {
                clickable.value = false
                onClick()
                scope.launch {
                    delay(clickInterval)
                    clickable.value = true
                }
            }
        )
    }

@Composable
private fun Modifier.singleClickable(
    scope: CoroutineScope = rememberCoroutineScope(),
    onClickLabel: String? = null,
    role: Role? = null,
    indication: Indication?,
    clickInterval: Long,
    onClick: () -> Unit
): Modifier =
    composed {
        val clickable = remember { mutableStateOf(true) }
        val interactionSource = remember { MutableInteractionSource() }

        Modifier.clickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = clickable.value,
            onClickLabel = onClickLabel,
            role = role,
            onClick = {
                clickable.value = false
                onClick()
                scope.launch {
                    delay(clickInterval)
                    clickable.value = true
                }
            }
        )
    }
