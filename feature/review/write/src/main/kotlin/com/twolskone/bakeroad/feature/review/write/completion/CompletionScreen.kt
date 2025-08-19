package com.twolskone.bakeroad.feature.review.write.completion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.review.write.R

@Composable
internal fun CompletionScreen(
    modifier: Modifier = Modifier,
    onGoHomeClick: () -> Unit,
    onConfirmReviewClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.feature_review_write_title_write_complete),
                style = BakeRoadTheme.typography.headingMediumBold,
                color = BakeRoadTheme.colorScheme.Primary500
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.feature_review_write_description_write_complete),
                style = BakeRoadTheme.typography.bodyMediumSemibold,
                color = BakeRoadTheme.colorScheme.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 183.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.weight(1f),
                    role = OutlinedButtonRole.SECONDARY,
                    size = ButtonSize.XLARGE,
                    onClick = onGoHomeClick,
                    content = { Text(text = stringResource(R.string.feature_review_write_button_go_home)) }
                )
                BakeRoadSolidButton(
                    modifier = Modifier.weight(1f),
                    role = SolidButtonRole.PRIMARY,
                    size = ButtonSize.XLARGE,
                    onClick = onConfirmReviewClick,
                    content = { Text(text = stringResource(R.string.feature_review_write_button_see_review)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompletionScreenPreview() {
    BakeRoadTheme {
        CompletionScreen(
            modifier = Modifier.fillMaxSize(),
            onGoHomeClick = {},
            onConfirmReviewClick = {}
        )
    }
}
