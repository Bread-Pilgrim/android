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
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.model.PreferenceOptionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

/**
 * 빵 취향 옵션 설정
 * @param optionList        취향 옵션 목록
 * @param selectedOptions   선택된 취향 옵션 IDs
 */
@Composable
fun PreferenceOptionsPage(
    modifier: Modifier = Modifier,
    page: Int,
    title: String,
    optionList: ImmutableList<PreferenceOption>,
    selectedOptions: ImmutableSet<Int>,
    onOptionSelected: (Boolean, PreferenceOption) -> Unit,
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
            text = stringResource(R.string.core_ui_description_preference),
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
            optionList.fastForEach { taste ->
                OptionChip(
                    selected = selectedOptions.contains(taste.id),
                    option = taste,
                    onSelected = onOptionSelected
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
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = selectedOptions.isNotEmpty(),
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
                Text(
                    text = stringResource(R.string.core_ui_button_next),
                    style = BakeRoadTheme.typography.bodyMediumSemibold
                )
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
private fun OptionChip(
    selected: Boolean,
    option: PreferenceOption,
    onSelected: (Boolean, PreferenceOption) -> Unit
) {
    BakeRoadChip(
        selected = selected,
        color = ChipColor.MAIN,
        size = ChipSize.LARGE,
        onSelectedChange = { onSelected(!selected, option) }
    ) {
        Text(text = option.name)
    }
}

private val DummyPreferenceOptions = listOf(
    PreferenceOption(id = 1, name = "페이스트리류 (크루아상, 뺑오쇼콜라)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 2, name = "담백한 식사용 빵 (식빵, 치아바타, 바케트, 하드롤)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 3, name = "건강한 빵 (비건, 글루텐프리, 저당)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 4, name = "구움과자 류 (마들렌, 휘낭시에, 까눌레)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 5, name = "클래식 & 레트로 빵 (단팥빵, 맘모스, 꽈배기, 크림빵", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 6, name = "달콤한 디저트 빵 (마카롱, 타르트)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 7, name = "달콤한 디저트 빵 (마카롱, 타르트)", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 8, name = "샌드위치 / 브런치 스타일", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 9, name = "케이크, 브라우니, 파이류", type = PreferenceOptionType.BREAD_TYPE),
    PreferenceOption(id = 9, name = "케이크, 파이류", type = PreferenceOptionType.BREAD_TYPE),
)

@Preview(showBackground = true)
@Composable
private fun PreferenceOptionsPagePreview() {
    BakeRoadTheme {
        PreferenceOptionsPage(
            page = 2,
            title = "빵 취향을 알려주세요!",
            optionList = DummyPreferenceOptions.toImmutableList(),
            selectedOptions = persistentSetOf(1),
            onOptionSelected = { _, _ -> },
            onPreviousPage = {},
            onNextPage = {},
            onComplete = {}
        )
    }
}