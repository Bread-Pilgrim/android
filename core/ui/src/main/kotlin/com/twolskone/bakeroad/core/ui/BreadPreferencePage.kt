package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.BreadPreference
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun BreadPreferencePage(
    modifier: Modifier = Modifier,
    page: Int,  /* 페이지 */
    title: String,  /* 페이지 제목 */
    breadPreferences: ImmutableList<BreadPreference>,   /* 빵 취향 목록 */
    selectedBreadPreferences: ImmutableSet<Int>,    /* 선택된 빵 취향 목록 */
    onPreferenceSelected: (BreadPreference) -> Unit,
    onPreviousPage: (Int) -> Unit,
    onNextPage: (Int) -> Unit,
    onComplete: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(horizontal = 16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        // Title.
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 67.dp, bottom = 6.dp),
            text = title,
            style = BakeRoadTheme.typography.headingLargeBold
        )
        // Description.
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.core_ui_description_bread_preference),
            style = BakeRoadTheme.typography.bodySmallRegular
        )
        // Page indicator.
        PageIndicator(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(fraction = 0.5f),
            page = page
        )
        // Chips.
        FlowRow(
            modifier = Modifier
                .padding(top = 42.dp)
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 2
        ) {
            breadPreferences.fastForEach { taste ->
                BreadPreferenceChip(
                    selected = selectedBreadPreferences.contains(taste.id),
                    breadPreference = taste,
                    onSelected = onPreferenceSelected
                )
            }
        }
        // Buttons.
        Box(
            modifier = Modifier
                .padding(bottom = 36.dp)
                .fillMaxWidth()
        ) {
            if (page > 1) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    role = OutlinedButtonVariant.PRIMARY,
                    size = ButtonSize.LARGE,
                    onClick = { onPreviousPage(page - 1) }
                ) {
                    Text(text = stringResource(R.string.core_ui_button_previous), style = BakeRoadTheme.typography.bodyMediumSemibold)
                }
            }
            BakeRoadSolidButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                enabled = selectedBreadPreferences.isNotEmpty(),
                role = SolidButtonVariant.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = {
                    if (page == 3) {
                        onComplete()
                    } else {
                        onNextPage(page + 1)
                    }
                }
            ) {
                Text(text = stringResource(R.string.core_ui_button_next), style = BakeRoadTheme.typography.bodyMediumSemibold)
            }
        }
    }
}

@Composable
private fun PageIndicator(modifier: Modifier, page: Int) {
    // TODO. 4페이지 이상일 때 확장성 고려?
    if (page <= 3) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicatorStep(number = 1, page = page)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color = BakeRoadTheme.colorScheme.Gray50, shape = CircleShape),
            )
            PageIndicatorStep(number = 2, page = page)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color = BakeRoadTheme.colorScheme.Gray50, shape = CircleShape),
            )
            PageIndicatorStep(number = 3, page = page)
        }
    }
}

private val StepSize = 24.dp

@Composable
private fun PageIndicatorStep(number: Int, page: Int) {
    if (number < page) {
        Image(
            modifier = Modifier.size(StepSize),
            imageVector = ImageVector.vectorResource(R.drawable.core_ui_ic_check_primary),
            contentDescription = "Check"
        )
    } else {
        val containerColor = if (number == page) BakeRoadTheme.colorScheme.Black else BakeRoadTheme.colorScheme.Gray50
        val contentColor = if (number == page) BakeRoadTheme.colorScheme.White else BakeRoadTheme.colorScheme.Gray300

        Box(
            modifier = Modifier
                .size(StepSize)
                .background(color = containerColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = BakeRoadTheme.typography.bodyXsmallSemibold.copy(color = contentColor),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BreadPreferenceChip(
    selected: Boolean,
    breadPreference: BreadPreference,
    onSelected: (BreadPreference) -> Unit
) {
    BakeRoadChip(
        selected = selected,
        color = ChipColor.MAIN,
        size = ChipSize.LARGE,
        onSelectedChange = { onSelected(breadPreference) }
    ) {
        Text(text = breadPreference.text)
    }
}

private val DummyBreadPreferences = listOf(
    BreadPreference(id = 1, text = "페이스트리류 (크루아상, 뺑오쇼콜라)"),
    BreadPreference(id = 2, text = "담백한 식사용 빵 (식빵, 치아바타, 바케트, 하드롤)"),
    BreadPreference(id = 3, text = "건강한 빵 (비건, 글루텐프리, 저당)"),
    BreadPreference(id = 4, text = "구움과자 류 (마들렌, 휘낭시에, 까눌레)"),
    BreadPreference(id = 5, text = "클래식 & 레트로 빵 (단팥빵, 맘모스, 꽈배기, 크림빵"),
    BreadPreference(id = 6, text = "달콤한 디저트 빵 (마카롱, 타르트)"),
    BreadPreference(id = 7, text = "달콤한 디저트 빵 (마카롱, 타르트)"),
    BreadPreference(id = 8, text = "샌드위치 / 브런치 스타일"),
    BreadPreference(id = 9, text = "케이크, 브라우니, 파이류"),
    BreadPreference(id = 9, text = "케이크, 파이류"),
)

@Preview(showBackground = true)
@Composable
private fun BreadPreferencePagePreview() {
    BakeRoadTheme {
        BreadPreferencePage(
            page = 2,
            title = "빵 취향을 알려주세요!",
            breadPreferences = DummyBreadPreferences.toImmutableList(),
            selectedBreadPreferences = persistentSetOf(1),
            onPreferenceSelected = {},
            onPreviousPage = {},
            onNextPage = {},
            onComplete = {}
        )
    }
}