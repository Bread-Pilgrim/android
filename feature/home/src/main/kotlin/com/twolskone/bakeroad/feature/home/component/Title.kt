package com.twolskone.bakeroad.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.home.R

@Composable
internal fun Title(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = title,
            style = BakeRoadTheme.typography.headingMediumBold
        )
        BakeRoadTextButton(
            modifier = Modifier.padding(start = 6.dp),
            style = TextButtonStyle.ASSISTIVE,
            size = TextButtonSize.SMALL,
            onClick = onSeeAllClick,
            text = { Text(text = stringResource(R.string.feature_home_button_see_all)) }
        )
    }
}

@Preview
@Composable
private fun TitlePreview() {
    BakeRoadTheme {
        Title(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White),
            title = stringResource(id = R.string.feature_home_title_my_preference_bakery),
            onSeeAllClick = {}
        )
    }
}