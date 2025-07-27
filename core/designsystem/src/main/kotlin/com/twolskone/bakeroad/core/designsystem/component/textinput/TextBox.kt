package com.twolskone.bakeroad.core.designsystem.component.textinput

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val TextFieldBoxShape = RoundedCornerShape(10.dp)

@Composable
fun BakeRoadTextBox(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    enabled: Boolean = true,
    maxLength: Int,
    lineLimits: TextFieldLineLimits = if (maxLength > 1) TextFieldLineLimits.MultiLine() else TextFieldLineLimits.SingleLine,
    inputTransformation: InputTransformation? = if (maxLength > 1) InputTransformation.maxLength(maxLength) else null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()
    val currentLength = state.text.length
    val activeCounterColor = BakeRoadTheme.colorScheme.Gray990
    val disabledCounterColor = BakeRoadTheme.colorScheme.Gray100
    val counterColor by remember(currentLength, maxLength) {
        derivedStateOf {
            if (currentLength <= 0) disabledCounterColor else activeCounterColor
        }
    }

    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        lineLimits = lineLimits,
        inputTransformation = inputTransformation,
        textStyle = BakeRoadTheme.typography.bodySmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray990),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        cursorBrush = SolidColor(value = BakeRoadTheme.colorScheme.Primary500),
        interactionSource = interactionSource,
        outputTransformation = outputTransformation,
        decorator = { textInput ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = BakeRoadTheme.colorScheme.White, shape = TextFieldBoxShape)
                    .border(
                        width = 1.dp,
                        color = if (isFocus) BakeRoadTheme.colorScheme.Primary500 else BakeRoadTheme.colorScheme.Gray300,
                        shape = TextFieldBoxShape
                    )
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                Box(modifier = Modifier.weight(1f, fill = true)) { textInput() }
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.End),
                    text = buildAnnotatedString {
                        withStyle(style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = counterColor).toSpanStyle()) {
                            append(state.text.length.toString())
                        }
                        withStyle(style = BakeRoadTheme.typography.body2XsmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray100).toSpanStyle()) {
                            append(" / $maxLength")
                        }
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BakeRoadTextFieldPreview() {
    BakeRoadTheme {
        BakeRoadTextBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            state = rememberTextFieldState(),
            maxLength = 30
        )
    }
}