package com.twolskone.bakeroad.feature.onboard.nickname

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.input.BakeRoadTextField
import com.twolskone.bakeroad.core.designsystem.component.input.TextFieldValidType
import com.twolskone.bakeroad.core.designsystem.component.loading.BakeRoadLoading
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.onboard.R

@Composable
internal fun NicknameSettingsScreen(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    description: String,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onStartClick: () -> Unit
) {
    val textFieldValidType = if (textFieldState.text.isEmpty()) {
        TextFieldValidType.DEFAULT
    } else if (description.isNotEmpty()) {
        TextFieldValidType.ERROR
    } else {
        TextFieldValidType.SUCCESS
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .singleClickable { onBackClick() }
                        .padding(4.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            }
        )
        Box {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                // Title.
                Text(
                    modifier = Modifier
                        .padding(top = 11.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.feature_onboarding_title_nickname_settings),
                    style = BakeRoadTheme.typography.headingLargeBold
                )
                // Description.
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    text = stringResource(R.string.feature_onboarding_description_nickname_settings),
                    style = BakeRoadTheme.typography.bodySmallRegular.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                BakeRoadTextField(
                    modifier = Modifier
                        .padding(top = 44.dp)
                        .fillMaxWidth(),
                    state = textFieldState,
                    validType = textFieldValidType,
                    title = stringResource(R.string.feature_onboarding_title_nickname),
                    description = description,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    onKeyboardAction = { performAction -> performAction() },
                )
                Spacer(modifier = Modifier.weight(1f))
                BakeRoadSolidButton(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(),
                    enabled = textFieldValidType == TextFieldValidType.SUCCESS,
                    style = SolidButtonStyle.PRIMARY,
                    size = ButtonSize.XLARGE,
                    trailingIcon = if (isLoading) {
                        { BakeRoadLoading() }
                    } else null,
                    text = {
                        Text(text = stringResource(id = R.string.feature_onboarding_button_bakeroad_start))
                    },
                    onClick = onStartClick
                )
            }
            // Block.
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleSingleClickable {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameSettingsScreenPreview() {
    BakeRoadTheme {
        val textFieldState = rememberTextFieldState("닉네임뭐로하지")

        NicknameSettingsScreen(
            modifier = Modifier.fillMaxSize(),
            textFieldState = textFieldState,
            description = "",
            isLoading = false,
            onBackClick = {},
            onStartClick = {}
        )
    }
}