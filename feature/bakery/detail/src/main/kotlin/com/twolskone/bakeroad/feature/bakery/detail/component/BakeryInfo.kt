package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.feature.bakery.detail.R
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryInfo

@Composable
internal fun BakeryInfo(
    modifier: Modifier = Modifier,
    bakeryInfo: BakeryInfo?
) {
    if (bakeryInfo != null) {
        Column(
            modifier = modifier
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = bakeryInfo.name,
                style = BakeRoadTheme.typography.bodyXlargeSemibold
            )
            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                    contentDescription = "RatingStar"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = bakeryInfo.rating.toString(),
                    style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.feature_bakery_detail_review_count, bakeryInfo.reviewCount),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            BakeRoadSolidButton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                style = SolidButtonStyle.PRIMARY,
                size = ButtonSize.MEDIUM,
                onClick = {},
                content = {
                    Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review))
                }
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                color = BakeRoadTheme.colorScheme.Gray50
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_location),
                    contentDescription = "Location",
                    tint = BakeRoadTheme.colorScheme.Gray800
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    text = bakeryInfo.address,
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                Text(
                    modifier = Modifier
                        .singleClickable { },
                    text = stringResource(R.string.feature_bakery_detail_button_view_map),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(
                        color = BakeRoadTheme.colorScheme.Gray950,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_clock),
                    contentDescription = "Location",
                    tint = BakeRoadTheme.colorScheme.Gray800
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = "월 10:00 ~ 20:00",
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                BakeRoadChip(
                    modifier = Modifier.padding(start = 6.dp),
                    selected = false,
                    color = ChipColor.LIGHT_GRAY,
                    size = ChipSize.SMALL,
                    label = { Text("휴무 없음") }
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_down_arrow),
                    contentDescription = "Location",
                    tint = BakeRoadTheme.colorScheme.Gray800
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_telephone),
                    contentDescription = "Location",
                    tint = BakeRoadTheme.colorScheme.Gray800
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    text = bakeryInfo.phone,
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                )
                Text(
                    modifier = Modifier
                        .singleClickable { },
                    text = stringResource(R.string.feature_bakery_detail_button_call),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(
                        color = BakeRoadTheme.colorScheme.Gray950,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    } else {
        // TODO. Skeleton.
    }
}

@Preview
@Composable
private fun BakeryInfoPreview() {
    BakeRoadTheme {
        BakeryInfo(
            bakeryInfo = BakeryInfo(
                name = "서라당",
                rating = 4.7f,
                reviewCount = 100,
                address = "서울시 관악구 신사로 120-1 1층 서라당",
                phone = "010-1234-5678",
                openStatus = BakeryOpenStatus.OPEN,
                isLike = true
            )
        )
    }
}