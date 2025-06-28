package com.twolskone.bakeroad.core.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val TextFieldShape = RoundedCornerShape(10.dp)

@Composable
fun BakeRoadTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    validType: TextFieldValidType = TextFieldValidType.DEFAULT,
    enabled: Boolean = true,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null,
    title: String = "",
    description: String = "",
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        text = title,
                        style = BakeRoadTheme.typography.bodyXsmallRegular
                    )
                }
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
                if (description.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = description,
                        style = BakeRoadTheme.typography.bodyXsmallRegular.copy(
                            color = validType.descriptionColor
                        )
                    )
                }
            }
        }
    )
}

enum class TextFieldValidType {
    DEFAULT, SUCCESS, ERROR;

    val descriptionColor: Color
        @Composable
        get() = when (this) {
            DEFAULT -> BakeRoadTheme.colorScheme.Gray990
            SUCCESS -> BakeRoadTheme.colorScheme.Success500
            ERROR -> BakeRoadTheme.colorScheme.Error400
        }
}

@Preview(showBackground = true)
@Composable
private fun BakeRoadTextFieldPreview() {
    BakeRoadTheme {
        BakeRoadTextField(
            state = rememberTextFieldState(),
            title = "Title",
            description = "디스크립션 내용입니다."
        )
    }
}