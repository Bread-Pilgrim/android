package com.twolskone.bakeroad.core.designsystem.component.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.R
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

@Composable
fun BakeRoadSearchTopAppBar(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String = "",
    containerColor: Color = BakeRoadTheme.colorScheme.White,
    iconContentColor: Color = BakeRoadTheme.colorScheme.Gray950,
    searchTextInputColors: SearchTextInputColors = BakeRoadSearchTopAppBarDefaults.searchBarColors(),
    showBackIcon: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .background(containerColor)
            .heightIn(max = TopAppBarMaxHeight)
            .padding(horizontal = 4.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = showBackIcon) {
            CompositionLocalProvider(
                LocalContentColor provides iconContentColor
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clip(CircleShape)
                        .singleClickable { onBackClick?.invoke() }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            }
        }
        SearchTextInput(
            modifier = Modifier.padding(horizontal = 12.dp),
            state = state,
            searchTextInputColors = searchTextInputColors,
            hint = hint
        )
    }
}

private val SearchTextInputShape = RoundedCornerShape(10.dp)

@Composable
private fun SearchTextInput(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    enabled: Boolean = true,
    searchTextInputColors: SearchTextInputColors,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    outputTransformation: OutputTransformation? = null,
    hint: String = ""
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = inputTransformation,
        textStyle = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = searchTextInputColors.contentColor),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        cursorBrush = SolidColor(value = BakeRoadTheme.colorScheme.Primary500),
        interactionSource = interactionSource,
        outputTransformation = outputTransformation,
        decorator = { textInput ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = searchTextInputColors.containerColor, shape = SearchTextInputShape),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isNotEmpty() || isFocus) {
                        textInput()
                    } else {
                        Text(
                            text = hint,
                            style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = searchTextInputColors.hintContentColor)
                        )
                    }
                }
                CompositionLocalProvider(
                    LocalContentColor provides searchTextInputColors.iconContentColor
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 12.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.core_designsystem_ic_search),
                        contentDescription = "Search"
                    )
                }
            }
        }
    )
}

object BakeRoadSearchTopAppBarDefaults {

    @Composable
    fun searchBarColors(
        containerColor: Color = BakeRoadTheme.colorScheme.Primary50,
        contentColor: Color = BakeRoadTheme.colorScheme.Gray950,
        hintContentColor: Color = BakeRoadTheme.colorScheme.Gray200,
        iconContentColor: Color = BakeRoadTheme.colorScheme.Black
    ) = SearchTextInputColors(
        containerColor = containerColor,
        contentColor = contentColor,
        hintContentColor = hintContentColor,
        iconContentColor = iconContentColor
    )
}

@Immutable
data class SearchTextInputColors(
    val containerColor: Color,
    val contentColor: Color,
    val hintContentColor: Color,
    val iconContentColor: Color
)

@Preview
@Composable
private fun BakeRoadSearchTopAppBarPreview() {
    BakeRoadTheme {
        BakeRoadSearchTopAppBar(
            state = rememberTextFieldState(),
            hint = "가고 싶은 빵집이나 메뉴를 검색해보세요.",
            showBackIcon = true
        )
    }
}