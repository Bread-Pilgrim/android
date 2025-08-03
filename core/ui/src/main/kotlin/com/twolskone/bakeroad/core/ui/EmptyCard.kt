package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private val EmptyCardShape = RoundedCornerShape(12.dp)

/**
 * 빈 화면
 */
@Composable
fun EmptyCard(
    modifier: Modifier = Modifier,
    description: String
) {
    Card(
        modifier = modifier,
        shape = EmptyCardShape,
        colors = CardColors(
            containerColor = BakeRoadTheme.colorScheme.Gray40,
            contentColor = BakeRoadTheme.colorScheme.Gray600,
            disabledContainerColor = BakeRoadTheme.colorScheme.Gray40,
            disabledContentColor = BakeRoadTheme.colorScheme.Gray600
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .align(Alignment.CenterHorizontally),
            text = description,
            style = BakeRoadTheme.typography.bodyXsmallRegular,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun EmptyCardPreview() {
    BakeRoadTheme {
        EmptyCard(
            modifier = Modifier.fillMaxWidth(),
            description = "아직 다녀온 빵집이 없어요 \uD83E\uDD72\n방문 후 리뷰를 남겨주시면 다녀온 빵집에 추가돼요!"
        )
    }
}