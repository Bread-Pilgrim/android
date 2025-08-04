package com.twolskone.bakeroad.feature.bakery.detail.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.common.kotlin.extension.toCommaString
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus
import com.twolskone.bakeroad.core.model.type.DayOfWeek
import com.twolskone.bakeroad.feature.bakery.detail.R
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.model.openingHourLabel
import com.twolskone.bakeroad.feature.bakery.detail.mvi.ReviewState
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun BakeryInfoSection(
    modifier: Modifier = Modifier,
    bakeryInfo: BakeryInfo?,
    reviewState: ReviewState,
    expandOpeningHour: Boolean,
    rotateOpeningHourIconAngle: Float,
    onExpandOpeningHourClick: () -> Unit,
    onWriteReviewClick: () -> Unit
) {
    if (bakeryInfo != null) {
        Column(
            modifier = modifier
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = bakeryInfo.name,
                style = BakeRoadTheme.typography.bodyXlargeSemibold
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.ui.R.drawable.core_ui_ic_star_yellow),
                    contentDescription = "RatingStar"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = reviewState.avgRating.toString(),
                    style = BakeRoadTheme.typography.bodySmallSemibold.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.feature_bakery_detail_review_count, reviewState.count.toCommaString()),
                    style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            BakeRoadSolidButton(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                role = SolidButtonRole.PRIMARY,
                size = ButtonSize.MEDIUM,
                onClick = onWriteReviewClick,
                content = { Text(text = stringResource(id = R.string.feature_bakery_detail_button_write_review)) }
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = BakeRoadTheme.colorScheme.Gray50
            )
            // 위치
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
            // 영업 시간
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_clock),
                        contentDescription = "Location",
                        tint = BakeRoadTheme.colorScheme.Gray800
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = bakeryInfo.openingHour.firstOrNull()?.openingHourLabel.orEmpty(),
                        style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                    )
                    BakeRoadChip(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        selected = false,
                        color = ChipColor.LIGHT_GRAY,
                        size = ChipSize.SMALL,
                        label = {
                            Text(
                                text = if (bakeryInfo.dayOff.isEmpty()) {
                                    stringResource(id = R.string.feature_bakery_detail_label_no_day_off)
                                } else {
                                    stringResource(id = R.string.feature_bakery_detail_label_day_off, bakeryInfo.dayOff.joinToString(separator = ","))
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(CircleShape)
                            .singleClickable { onExpandOpeningHourClick() }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.rotate(rotateOpeningHourIconAngle),
                            imageVector = ImageVector.vectorResource(id = R.drawable.feature_bakery_detail_ic_down_arrow),
                            contentDescription = "Location",
                            tint = BakeRoadTheme.colorScheme.Gray800
                        )
                    }
                }
                if (expandOpeningHour) {
                    for (i in 1 until bakeryInfo.openingHour.size) {
                        val item = bakeryInfo.openingHour[i]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                text = item.openingHourLabel,
                                style = BakeRoadTheme.typography.bodyXsmallMedium.copy(color = BakeRoadTheme.colorScheme.Gray800)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            // 전화
            if (bakeryInfo.phone.isNotBlank()) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
        }
    } else {
        // TODO. Skeleton.
    }
}

@Preview
@Composable
private fun BakeryInfoSectionPreview() {
    BakeRoadTheme {
        BakeryInfoSection(
            bakeryInfo = BakeryInfo(
                name = "서라당",
                address = "서울시 관악구 신사로 120-1 1층 서라당",
                phone = "010-1234-5678",
                openStatus = BakeryOpenStatus.OPEN,
                openingHour = persistentListOf(
                    BakeryInfo.OpeningHour(
                        dayOffWeek = DayOfWeek.MONDAY,
                        openTime = "10:00",
                        closeTime = "20:00"
                    )
                ),
                dayOff = persistentListOf(),
                isLike = true
            ),
            reviewState = ReviewState(),
            expandOpeningHour = false,
            rotateOpeningHourIconAngle = 0f,
            onExpandOpeningHourClick = {},
            onWriteReviewClick = {}
        )
    }
}