package com.twolskone.bakeroad.core.designsystem.component.popup

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonVariant

enum class PopupButton {
    SHORT, LONG
}

@Composable
internal fun BakeRoadPopupButtons(
    buttonType: PopupButton,
    primaryText: String,
    secondaryText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    when (buttonType) {
        PopupButton.SHORT -> {
            Row(modifier = Modifier.fillMaxWidth()) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.weight(1f),
                    role = OutlinedButtonVariant.SECONDARY,
                    size = ButtonSize.XLARGE,
                    onClick = onSecondaryClick,
                    content = { Text(text = primaryText) }
                )
                BakeRoadSolidButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    role = SolidButtonVariant.PRIMARY,
                    size = ButtonSize.XLARGE,
                    onClick = onPrimaryClick,
                    content = { Text(text = secondaryText) }
                )
            }
        }

        PopupButton.LONG -> {
            BakeRoadSolidButton(
                modifier = Modifier.fillMaxWidth(),
                role = SolidButtonVariant.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = onPrimaryClick,
                content = { Text(text = primaryText) }
            )
            BakeRoadOutlinedButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                role = OutlinedButtonVariant.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = onSecondaryClick,
                content = { Text(text = secondaryText) }
            )
        }
    }
}