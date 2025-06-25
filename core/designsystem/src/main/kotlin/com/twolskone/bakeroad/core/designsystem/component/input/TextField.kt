package com.twolskone.bakeroad.core.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val TextFieldShape = RoundedCornerShape(10.dp)

@Composable
fun BakeRoadTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        inputTransformation = inputTransformation,
        textStyle = BakeRoadTheme.typography.bodySmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray990),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        cursorBrush = SolidColor(value = BakeRoadTheme.colorScheme.Primary500),
        interactionSource = interactionSource,
        outputTransformation = outputTransformation,
        decorator = { textInput ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White, shape = TextFieldShape)
                    .border(
                        width = 1.dp,
                        color = if (isFocus) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray100,
                        shape = TextFieldShape
                    )
                    .padding(horizontal = 16.dp, vertical = 13.5.dp)
            ) {
                textInput()
            }
        }
    )
}

@Preview
@Composable
private fun BakeRoadTextFieldPreview() {
    BakeRoadTheme {
        BakeRoadTextField(
            state = rememberTextFieldState()
        )
    }
}