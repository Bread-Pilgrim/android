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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme

private const val ReviewContentMaxLength = 300

@Composable
internal fun WriteReviewScreen(
    modifier: Modifier = Modifier,
    onAddPhotoClick: () -> Unit
) {
    val contentTextState = rememberTextFieldState(initialText = "")

    Box(modifier = modifier.background(color = BakeRoadTheme.colorScheme.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            BakeRoadTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                leftActions = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = BakeRoadTheme.colorScheme.White.copy(alpha = 0.6f))
                            .singleClickable {}
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        BakeRoadSwitch(
                            checked = false,
                            size = SwitchSize.SMALL,
                            onCheckedChange = {}
                        )
                        BakeRoadTextButton(
                            style = TextButtonStyle.ASSISTIVE,
                            size = TextButtonSize.MEDIUM,
                            onClick = {},
                            content = { Text(text = stringResource(id = R.string.feature_review_write_button_private_review)) }
                        )
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BakeRoadRatingBar(
                    rating = 2.5f,
                    onRatingChange = {}
                )
                Text(
                    text = "2.5",
                    style = BakeRoadTheme.typography.bodyXlargeMedium.copy(color = BakeRoadTheme.colorScheme.Gray950)
                )
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(count = 10) {
                    BakeRoadChip(
                        selected = false,
                        color = ChipColor.LIGHT_GRAY,
                        size = ChipSize.LARGE,
                        label = { Text(text = "꿀고구마 휘낭시에") }
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
//            enabled = false,
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
                items(count = 10) {
                    ReviewPhoto("")
                }
            }
        }
        BakeRoadSolidButton(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            style = SolidButtonStyle.PRIMARY,
            size = ButtonSize.XLARGE,
            onClick = {},
            content = { Text(text = stringResource(id = R.string.feature_review_write_button_write_complete)) }
        )
    }
}

private val PhotoSize = 100.dp
private val PhotoShape = RoundedCornerShape(12.dp)

@Composable
private fun ReviewPhoto(url: String) {
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
        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.feature_review_write_ic_circle_close),
            contentDescription = "Delete"
        )
    }
}

@Preview
@Composable
private fun WriteReviewScreenPreview() {
    BakeRoadTheme {
        WriteReviewScreen(
            modifier = Modifier.fillMaxSize(),
            onAddPhotoClick = {}
        )
    }
}