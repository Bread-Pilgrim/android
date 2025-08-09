package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.extension.shimmerEffect
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.PreferenceOption
import com.twolskone.bakeroad.core.model.PreferenceOptionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

private val PageIndicatorWidth = 164.dp
private const val TotalPage = 3

/**
 * 취향 설정 페이지
 * @param totalPage         총 페이지 (default: 3)
 * @param page              현재 페이지
 * @param completionText    마지막 페이지 완료 버튼 텍스트 (default: 저장)
 * @param optionList        취향 옵션 목록
 * @param selectedOptions   선택된 취향 옵션 IDs
 */
@Composable
fun PreferenceOptionListPage(
    modifier: Modifier = Modifier,
    loading: Boolean,
    totalPage: Int = TotalPage,
    page: Int,
    title: String,
    completionText: String = stringResource(id = R.string.core_ui_button_save),
    optionList: ImmutableList<PreferenceOption>,
    selectedOptions: ImmutableSet<Int>,
    onOptionSelected: (Boolean, PreferenceOption) -> Unit,
    onPreviousPage: (Int) -> Unit,
    onNextPage: (Int) -> Unit,
    onComplete: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp, bottom = 6.dp),
            text = title,
            style = BakeRoadTheme.typography.headingLargeBold
        )
        // Description
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.core_ui_description_preference),
            style = BakeRoadTheme.typography.bodySmallRegular
        )
        // Page indicator
        PageIndicator(
            modifier = Modifier
                .padding(top = 25.dp)
                .width(PageIndicatorWidth),
            totalPage = totalPage,
            currentPage = page
        )
        // Chips
        if (loading) {
            OptionChipsSkeleton(
                modifier = Modifier
                    .padding(top = 42.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
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
        }
        // Buttons
        Box(
            modifier = Modifier
                .padding(bottom = 36.dp)
                .fillMaxWidth()
        ) {
            if (page > 1) {
                BakeRoadOutlinedButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    role = OutlinedButtonRole.PRIMARY,
                    size = ButtonSize.LARGE,
                    onClick = { onPreviousPage(page - 1) }
                ) {
                    Text(text = stringResource(R.string.core_ui_button_previous), style = BakeRoadTheme.typography.bodyMediumSemibold)
                }
            }
            BakeRoadSolidButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = selectedOptions.isNotEmpty(),
                role = SolidButtonRole.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = {
                    if (page == totalPage) {
                        onComplete()
                    } else {
                        onNextPage(page + 1)
                    }
                }
            ) {
                Text(
                    text = if (page == totalPage) completionText else stringResource(R.string.core_ui_button_next),
                    style = BakeRoadTheme.typography.bodyMediumSemibold
                )
            }
        }
    }
}

@Composable
private fun PageIndicator(
    modifier: Modifier,
    totalPage: Int,
    currentPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..totalPage).forEach { page ->
            PageIndicatorStep(page = page, currentPage = currentPage)
            if (page < totalPage) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(color = BakeRoadTheme.colorScheme.Gray50, shape = CircleShape),
                )
            }
        }
    }
}

private val StepSize = 24.dp

@Composable
private fun PageIndicatorStep(page: Int, currentPage: Int) {
    if (page < currentPage) {
        Image(
            modifier = Modifier.size(StepSize),
            imageVector = ImageVector.vectorResource(R.drawable.core_ui_ic_check_primary),
            contentDescription = "Check"
        )
    } else {
        val containerColor = if (page == currentPage) BakeRoadTheme.colorScheme.Black else BakeRoadTheme.colorScheme.Gray50
        val contentColor = if (page == currentPage) BakeRoadTheme.colorScheme.White else BakeRoadTheme.colorScheme.Gray300

        Box(
            modifier = Modifier
                .size(StepSize)
                .background(color = containerColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = page.toString(),
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

@Composable
private fun OptionChipsSkeleton(
    modifier: Modifier = Modifier,
    chipWidths: Array<Dp> = arrayOf(222.dp, 295.dp, 209.dp, 229.dp, 304.dp, 205.dp, 157.dp, 155.dp)
) {
    FlowRow(
        modifier = modifier.wrapContentHeight(unbounded = true, align = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2
    ) {
        chipWidths.forEach { width ->
            Box(
                modifier = Modifier
                    .size(width = width, height = 30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
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
private fun PreferenceOptionListPagePreview() {
    BakeRoadTheme {
        val loading = true
        PreferenceOptionListPage(
            loading = loading,
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