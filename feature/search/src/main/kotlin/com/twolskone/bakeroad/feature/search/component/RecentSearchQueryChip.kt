package com.twolskone.bakeroad.feature.search.component

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.search.R

@Composable
internal fun RecentSearchQueryChip(
    query: String,
    onClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    BakeRoadOutlinedButton(
        role = OutlinedButtonRole.ASSISTIVE,
        size = ButtonSize.SMALL,
        style = ButtonStyle.ROUND,
        onClick = { onClick(query) },
        text = { Text(text = query) },
        trailingIcon = {
            Icon(
                modifier = Modifier.noRippleSingleClickable { onDeleteClick(query) },
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_search_ic_delete),
                contentDescription = "Delete"
            )
        }
    )
}

@Preview
@Composable
private fun RecentSearchQueryChipPreview() {
    BakeRoadTheme {
        RecentSearchQueryChip(
            query = "서라당",
            onClick = {},
            onDeleteClick = {}
        )
    }
}