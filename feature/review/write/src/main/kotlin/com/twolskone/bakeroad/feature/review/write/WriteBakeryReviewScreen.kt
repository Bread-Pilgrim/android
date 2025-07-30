package com.twolskone.bakeroad.feature.review.write

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.chip.BakeRoadChip
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipColor
import com.twolskone.bakeroad.core.designsystem.component.chip.ChipSize
import com.twolskone.bakeroad.core.designsystem.component.ratingbar.BakeRoadRatingBar
import com.twolskone.bakeroad.core.designsystem.component.switch.BakeRoadSwitch
import com.twolskone.bakeroad.core.designsystem.component.switch.SwitchSize
import com.twolskone.bakeroad.core.designsystem.component.textinput.BakeRoadTextBox
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.extension.noRippleSingleClickable
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.review.write.mvi.WriteBakeryReviewState

private const val ReviewContentMaxLength = 300

@Composable
internal fun WriteBakeryReviewScreen(
    modifier: Modifier = Modifier,
    state: WriteBakeryReviewState,
    contentTextState: TextFieldState,
    onAddPhotoClick: () -> Unit,
    onBackClick: () -> Unit,
    onRatingChange: (Float) -> Unit,
    onDeletePhotoClick: (Int) -> Unit,
    onPrivateCheck: (Boolean) -> Unit,
    onSubmit: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            leftActions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                        .singleClickable { onBackClick() }
                        .padding(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back),
                        contentDescription = "Back"
                    )
                }
            },
            title = {
                Text(text = stringResource(id = R.string.feature_review_write_title_write_review))
            },
            rightActions = {
                Row(
                    modifier = Modifier.padding(end = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BakeRoadSwitch(
                        checked = state.isPrivate,
                        size = SwitchSize.SMALL,
                        onCheckedChange = onPrivateCheck
                    )
                    Text(
                        text = stringResource(id = R.string.feature_review_write_button_private_review),
                        style = BakeRoadTheme.typography.bodyMediumSemibold.copy(color = BakeRoadTheme.colorScheme.Gray800)
                    )
                }
            }
        )
        Column(
            modifier = modifier
                .padding(top = 56.dp, bottom = 100.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BakeRoadRatingBar(
                    rating = state.rating,
                    onRatingChange = onRatingChange
                )
                Text(
                    modifier = Modifier.width(30.dp),
                    text = state.rating.toString(),
                    style = BakeRoadTheme.typography.bodyXlargeMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(
                    items = state.selectedMenuList,
                    key = { menu -> menu.id }
                ) { menu ->
                    BakeRoadChip(
                        selected = false,
                        color = ChipColor.LIGHT_GRAY,
                        size = ChipSize.LARGE,
                        label = { Text(text = menu.name) }
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = BakeRoadTheme.colorScheme.Gray50
            )
            BakeRoadTextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 15.dp)
                    .height(150.dp),
                state = contentTextState,
                maxLength = ReviewContentMaxLength
            )
            BakeRoadTextButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                enabled = state.photoList.size < 5,
                style = TextButtonStyle.PRIMARY,
                size = TextButtonSize.MEDIUM,
                onClick = onAddPhotoClick,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_plus),
                        contentDescription = "AddPhoto"
                    )
                },
                text = {
                    Text(text = stringResource(id = R.string.feature_review_write_button_add_photo))
                }
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(items = state.photoList) { index, url ->
                    ReviewPhoto(
                        index = index,
                        url = url,
                        onDeleteClick = onDeletePhotoClick
                    )
                }
            }
        }
        BakeRoadSolidButton(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            enabled = contentTextState.text.isNotEmpty(),
            style = SolidButtonStyle.PRIMARY,
            size = ButtonSize.XLARGE,
            onClick = onSubmit,
            content = { Text(text = stringResource(id = R.string.feature_review_write_button_write_complete)) }
        )
    }
}

private val PhotoSize = 100.dp
private val PhotoShape = RoundedCornerShape(12.dp)

@Composable
private fun ReviewPhoto(
    index: Int,
    url: String,
    onDeleteClick: (Int) -> Unit
) {
    Box(modifier = Modifier.size(PhotoSize)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(PhotoShape),
            model = url,
            contentDescription = "ReviewPhoto",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_thumbnail)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .noRippleSingleClickable { onDeleteClick(index) }
                .padding(6.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_circle_close),
                contentDescription = "Delete"
            )
        }
    }
}

@Preview
@Composable
private fun WriteBakeryReviewScreenPreview() {
    BakeRoadTheme {
        WriteBakeryReviewScreen(
            modifier = Modifier.fillMaxSize(),
            state = WriteBakeryReviewState(),
            contentTextState = rememberTextFieldState(),
            onAddPhotoClick = {},
            onBackClick = {},
            onRatingChange = {},
            onDeletePhotoClick = {},
            onPrivateCheck = {},
            onSubmit = {}
        )
    }
}