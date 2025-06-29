package com.twolskone.bakeroad.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonVariant
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList

// DUMMY
data class Taste(
    val id: Int,
    val taste: String
)

@Composable
fun TasteSelectionPage(
    modifier: Modifier = Modifier,
    page: Int,
    tastes: ImmutableList<Taste>,
    selectedTastes: ImmutableSet<Int>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(color = BakeRoadTheme.colorScheme.White),
        horizontalAlignment = Alignment.Start
    ) {
        // Title.
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 67.dp, bottom = 6.dp),
            text = stringResource(R.string.core_ui_title_taste_selection),
            style = BakeRoadTheme.typography.headingLargeBold
        )
        // Description.
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.core_ui_description_taste_selection),
            style = BakeRoadTheme.typography.bodySmallRegular
        )
        // Step indicator.
        Indicator(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(fraction = 0.5f),
            page = page
        )
        // Chips.
        LazyColumn(
            modifier = Modifier
                .padding(top = 42.dp)
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = tastes, key = { it.id }) { taste ->
                TasteChip(
                    selected = selectedTastes.contains(taste.id),
                    taste = taste,
                    onSelected = {}
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
                    onClick = {}
                ) {
                    Text(text = stringResource(R.string.core_ui_button_previous), style = BakeRoadTheme.typography.bodyMediumSemibold)
                }
            }
            BakeRoadSolidButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                enabled = selectedTastes.isNotEmpty(),
                role = SolidButtonVariant.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = {}
            ) {
                Text(text = stringResource(R.string.core_ui_button_next), style = BakeRoadTheme.typography.bodyMediumSemibold)
            }
        }
    }
}

@Composable
private fun Indicator(modifier: Modifier, page: Int) {
    // TODO. 4페이지 이상일 때 확장성 고려?
    if (page <= 3) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IndicatorStep(number = 1, page = page)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color = BakeRoadTheme.colorScheme.Gray50, shape = CircleShape),
            )
            IndicatorStep(number = 2, page = page)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color = BakeRoadTheme.colorScheme.Gray50, shape = CircleShape),
            )
            IndicatorStep(number = 3, page = page)
        }
    }
}

private val StepSize = 24.dp

@Composable
private fun IndicatorStep(number: Int, page: Int) {
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
private fun TasteChip(
    selected: Boolean,
    taste: Taste,
    onSelected: (Int) -> Unit
) {
    BakeRoadChip(
        selected = selected,
        color = ChipColor.MAIN,
        size = ChipSize.LARGE,
        onSelectedChange = { onSelected(taste.id) }
    ) {
        Text(text = taste.taste)
    }
}

private val DummyTastes = listOf(
    Taste(id = 1, taste = "페이스트리류 (크루아상, 뺑오쇼콜라)"),
    Taste(id = 2, taste = "담백한 식사용 빵 (식빵, 치아바타, 바케트, 하드롤)"),
    Taste(id = 3, taste = "건강한 빵 (비건, 글루텐프리, 저당)"),
    Taste(id = 4, taste = "구움과자 류 (마들렌, 휘낭시에, 까눌레)"),
    Taste(id = 5, taste = "클래식 & 레트로 빵 (단팥빵, 맘모스, 꽈배기, 크림빵"),
    Taste(id = 6, taste = "달콤한 디저트 빵 (마카롱, 타르트)"),
    Taste(id = 7, taste = "달콤한 디저트 빵 (마카롱, 타르트)"),
    Taste(id = 8, taste = "샌드위치 / 브런치 스타일"),
    Taste(id = 9, taste = "케이크, 브라우니, 파이류"),
)

@Preview(showBackground = true)
@Composable
private fun TasteSelectionPagePreview() {
    BakeRoadTheme {
        TasteSelectionPage(
            page = 2,
            tastes = DummyTastes.toImmutableList(),
            selectedTastes = persistentSetOf(1)
        )
    }
}