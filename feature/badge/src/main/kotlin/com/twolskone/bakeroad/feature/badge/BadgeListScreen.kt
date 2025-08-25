package com.twolskone.bakeroad.feature.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadOutlinedButton
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadSolidButton
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.ButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.button.OutlinedButtonRole
import com.twolskone.bakeroad.core.designsystem.component.button.SolidButtonRole
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBar
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadTopAppBarIcon
import com.twolskone.bakeroad.core.designsystem.extension.singleClickable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.ui.BakeRoadBadge
import com.twolskone.bakeroad.feature.badge.mvi.BadgeListState

@Composable
internal fun BadgeListScreen(
    modifier: Modifier = Modifier,
    state: BadgeListState,
    onBackClick: () -> Unit,
    onEnableBadge: (Badge) -> Unit,
    onDisableBadge: (Badge) -> Unit
) {
    var selectedBadge by remember { mutableStateOf<Badge?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .systemBarsPadding()
    ) {
        BakeRoadTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            leftActions = {
                BakeRoadTopAppBarIcon(
                    iconRes = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_back,
                    contentDescription = "Back",
                    onClick = onBackClick
                )
            },
            title = { Text(text = stringResource(id = R.string.feature_badge_list)) }
        )
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
                .systemBarsPadding(),
            columns = GridCells.Fixed(3)
        ) {
            item(span = { GridItemSpan(3) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BakeRoadTheme.colorScheme.White)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BakeRoadBadge(
                        size = 90.dp,
                        imageSize = 72.dp,
                        badge = state.representativeBadge
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(id = R.string.feature_badge_representative_badge_of, "김빵글"),
                        style = BakeRoadTheme.typography.bodyMediumSemibold,
                        color = BakeRoadTheme.colorScheme.Primary500
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = state.representativeBadge.name.ifBlank { stringResource(id = R.string.feature_badge_none) },
                        style = BakeRoadTheme.typography.bodyXsmallMedium,
                        color = BakeRoadTheme.colorScheme.Gray600
                    )
                    BakeRoadOutlinedButton(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        role = OutlinedButtonRole.ASSISTIVE,
                        style = ButtonStyle.DEFAULT,
                        size = ButtonSize.MEDIUM,
                        onClick = { selectedBadge = state.representativeBadge },
                        content = { Text(text = stringResource(id = R.string.feature_badge_button_update_badge)) }
                    )
                }
            }
            item(span = { GridItemSpan(3) }) {
                HorizontalDivider(
                    color = BakeRoadTheme.colorScheme.Gray50,
                    thickness = 8.dp
                )
            }
            item(span = { GridItemSpan(3) }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = BakeRoadTheme.colorScheme.White)
                        .padding(top = 20.dp, bottom = 16.dp)
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.feature_badge_list),
                    style = BakeRoadTheme.typography.bodyLargeSemibold
                )
            }
            itemsIndexed(
                items = state.badgeList,
                key = { _, badge -> badge.id }
            ) { index, badge ->
                Column(
                    modifier = Modifier
                        .then(
                            if (index % 3 == 0) {
                                Modifier.padding(start = 6.dp)
                            } else if (index % 3 == 2) {
                                Modifier.padding(end = 6.dp)
                            } else {
                                Modifier
                            }
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = BakeRoadTheme.colorScheme.White)
                        .then(
                            if (badge.isEarned) {
                                Modifier.singleClickable { selectedBadge = badge }
                            } else {
                                Modifier
                            }
                        )
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BakeRoadBadge(
                        size = 84.dp,
                        imageSize = 72.dp,
                        badge = badge
                    )
                    Text(
                        text = badge.name,
                        style = BakeRoadTheme.typography.bodyXsmallMedium,
                        color = BakeRoadTheme.colorScheme.Gray600,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    selectedBadge?.let { badge ->
        BadgeSheet(
            modifier = Modifier.fillMaxWidth(),
            badge = badge,
            onDismissRequest = { selectedBadge = null },
            onPrimaryClick = {
                if (badge.isRepresentative) {
                    onDisableBadge(badge)
                } else {
                    onEnableBadge(badge)
                }
                selectedBadge = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BadgeSheet(
    modifier: Modifier = Modifier,
    badge: Badge,
    onDismissRequest: () -> Unit = {},
    onPrimaryClick: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = BakeRoadTheme.colorScheme.White,
        contentColor = BakeRoadTheme.colorScheme.Gray990,
        onDismissRequest = onDismissRequest,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BakeRoadTheme.colorScheme.White)
                .padding(top = 2.dp)
                .padding(horizontal = 16.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BakeRoadBadge(
                size = 90.dp,
                imageSize = 72.dp,
                badge = Badge.ofEmpty()
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = badge.name,
                style = BakeRoadTheme.typography.bodyMediumSemibold
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = badge.description,
                style = BakeRoadTheme.typography.bodyXsmallMedium,
                color = BakeRoadTheme.colorScheme.Gray600,
                textAlign = TextAlign.Center
            )
            BakeRoadSolidButton(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                role = if (badge.isRepresentative) SolidButtonRole.PRIMARY else SolidButtonRole.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = onPrimaryClick,
                content = {
                    Text(
                        text = stringResource(
                            id = if (badge.isRepresentative) {
                                R.string.feature_badge_button_remove_badge
                            } else {
                                R.string.feature_badge_button_set_badge
                            }
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun BadgeListScreenPreview() {
    BakeRoadTheme {
        BadgeListScreen(
            state = BadgeListState(),
            onBackClick = {},
            onEnableBadge = {},
            onDisableBadge = {}
        )
    }
}

private val DummyBadges = listOf(
    Badge(2, "첫 리뷰 뱃지", "첫 리뷰를 달아서 커뮤니티에 발을 내디뎠어요! 🎉", "", true, true),
    Badge(1, "새싹 빵러", "처음이 소중해요~ 빵지순례 시작을 축하해요!", "", true, false),
    Badge(4, "리뷰 팬", "50개의 리뷰로 빵집 지도를 풍성하게 만들었어요! 💬", "", false, false),
    Badge(5, "리뷰 전문가", "100개의 리뷰로 빵 순례의 고수가 되었어요! 🌟", "", false, false),
    Badge(6, "리뷰 대가", "500개의 리뷰로 빵계의 레전드가 되셨네요! 🏆", "", false, false),
    Badge(7, "결 따라 탐험가", "크루아상과 빵오쇼콜라로 고소한 여정을 시작했어요.", "", false, false),
    Badge(8, "버터 향수", "100개의 페이스트리로 버터 향을 음미했어요.", "", false, false),
    Badge(9, "결의 대가", "500번의 페이스트리와 함께한 버터 마니아!", "", false, false),
    Badge(10, "식사빵 초보", "식빵부터 치아바타까지, 든든한 한 끼를 채웠어요.", "", false, false),
    Badge(11, "빵식 장인", "100번의 식사용 빵으로 포만감 아티스트가 되었어요.", "", false, false),
    Badge(12, "밀덕", "500의탐험으로 식사를 대신한 미식가!", "", false, false),
    Badge(13, "비건 도전가", "비건 메뉴10번으로 몸과 마음에 건강을 채웠어요.", "", false, false),
    Badge(14, "글루텐 프리 매니아", "글루텐 프리 100가지를 정복했어요.", "", false, false),
    Badge(15, "웰빙 열정가", "건강빵 500번 시식으로 당 없애기 챔피언!", "", false, false),
    Badge(16, "달콤 구움쟁이", "마들렌과 까눌레로 달콤함을 곱씹었어요.", "", false, false),
    Badge(17, "버터 바삭러", "100번의 구움과자로 버터의 진가를 경험했어요.", "", false, false),
    Badge(18, "카날레 마스터", "500종 구움과자 탐방으로 디저트 장인이 되었어요.", "", false, false),
    Badge(19, "추억 소환가", "단팥빵과 꽈배기로 어린 시절을 소환했어요.", "", false, false),
    Badge(20, "추억 탐험가", "100번의 레트로 빵으로 시간 여행을 즐겼어요.", "", false, false),
    Badge(21, "시간 여행자", "500종 레트로 빵으로 과거의 감성을 수집했어요.", "", false, false),
    Badge(22, "달콤한 유혹", "마카롱과 타르트로 달콤함을 느꼈어요.", "", false, false),
    Badge(23, "스위트 헌터", "100번의 디저트로 단맛 사냥을 완료했어요.", "", false, false),
    Badge(24, "달콤 마니아", "500개의 디저트빵으로 달콤함의 정점에 섰어요.", "", false, false),
    Badge(25, "모닝 퀸", "샌드위치로 시작하는 아침의 우아함을 맛봤어요.", "", false, false),
    Badge(26, "브런치 아티스트", "100회의 브런치로 레스토랑 부럽지 않은 하루!", "", false, false),
    Badge(27, "브런치 제왕", "500번의 브런치로 여유의 달인이 되었어요.", "", false, false),
    Badge(28, "베이커리 스위트", "케이크 한 조각으로 달콤한 순간을 기록했어요.", "", false, false),
    Badge(29, "파이 크레이지", "100번의 파이로 바삭함의 중독에 빠졌어요.", "", false, false),
    Badge(30, "스위트 제왕", "500종 스위트로 황금의 맛을 완성했어요.", "", false, false),
    Badge(31, "찜쟁이", "10곳의 빵집을 찜한 열혈 팬!", "", false, false),
    Badge(32, "슈퍼 찜러", "100곳의 빵집을 골라 저장한 애호가!", "", false, false),
    Badge(33, "찜왕", "500곳의 빵집을 찜해둔 궁극의 팬!", "", false, false),
    Badge(34, "첫 발자국", "10곳의 빵집으로 순례의 시작을 알렸어요.", "", false, false),
    Badge(35, "방문자", "100곳의 빵집에서 다른 맛을 경험했어요.", "", false, false),
    Badge(36, "빵 마니아", "500곳 빵집 탐험으로 팬심을 증명했어요.", "", false, false),
    Badge(37, "인기 리뷰어 I", "10회의 응원으로 리뷰가 빛났어요.", "", false, false),
    Badge(38, "인기 리뷰어 II", "100회의 좋아요로 사랑받는 리뷰어!", "", false, false),
    Badge(39, "인기 리뷰어 III", "500회의 좋아요로 빵글 스타!", "", false, false),
    Badge(40, "좋아요 요정 I", "10회의 공감으로 따뜻함을 나눴어요.", "", false, false),
    Badge(41, "좋아요 요정 II", "100회의 클릭으로 마음을 전했어요.", "", false, false),
    Badge(42, "좋아요 요정 III", "500회의 좋아요로 소통의 달인이었어요.", "", false, false),
    Badge(43, "충성 빵순이", "10일 동안 빵 사랑을 이어갔어요.", "", false, false),
    Badge(44, "열혈 빵순이", "50일 연속으로 앱에서 함께했어요.", "", false, false),
    Badge(45, "전설의 빵순이", "100일의 충성을 자랑하는 전설!", "", false, false),
    Badge(46, "첫 투자", "처음으로 10만원어치의 빵으로 하루를 더 풍성히!", "", false, false),
    Badge(47, "빵 투자자", "백만원어치 빵으로 맛의 가치를 증명했어요.", "", false, false),
    Badge(48, "빵 재벌", "오백만원어치 빵 탐방으로 황금 입맛 완성!", "", false, false),
    Badge(49, "로컬 탐험가", "부산 곳곳에서 빵 탐험을 시작했어요.", "", false, false),
    Badge(50, "광역 탐험가", "10개 지역을 누비며 빵지도에 흔적을 남겼어요.", "", false, false),
    Badge(3, "리뷰 초보 마스터", "10개의 리뷰로 빵집 탐방기를 공유했어요! 📝", "", false, false)
)