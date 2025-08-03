package com.twolskone.bakeroad.feature.search.component

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
import com.twolskone.bakeroad.feature.search.R

@Composable
internal fun SectionTitle(
    title: String,
    deleteAllEnabled: Boolean,
    onDeleteAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = BakeRoadTheme.typography.bodyLargeSemibold
        )
        BakeRoadTextButton(
            style = TextButtonStyle.ASSISTIVE,
            size = TextButtonSize.SMALL,
            enabled = deleteAllEnabled,
            onClick = onDeleteAllClick,
            content = { Text(text = stringResource(id = R.string.feature_search_button_delete_all)) }
        )
    }
}

@Preview
@Composable
private fun TitlePreview() {
    BakeRoadTheme {
        SectionTitle(
            title = "최근 조회한 빵집",
            deleteAllEnabled = true,
            onDeleteAllClick = {}
        )
    }
}