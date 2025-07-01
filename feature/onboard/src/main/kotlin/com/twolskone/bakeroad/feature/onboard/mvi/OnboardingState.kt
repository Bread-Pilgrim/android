package com.twolskone.bakeroad.feature.onboard.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.BreadPreference
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Immutable
internal data class OnboardingState(
    val preferenceState: PreferenceState = PreferenceState(),
    val nicknameSettingsState: NicknameSettingsState = NicknameSettingsState()
) : BaseUiState

@Immutable
internal data class PreferenceState(
    val page: Int = 1,
    val breadPreferenceList: ImmutableList<BreadPreference> = DummyBreadPreferenceList,
    val breadTasteList: ImmutableList<BreadPreference> = DummyBreadTasteList,
    val bakeryPreferenceList: ImmutableList<BreadPreference> = DummyBakeryPreferenceList,
    val selectedBreadPreferences: ImmutableSet<Int> = persistentSetOf(1),
    val selectedBreadTastes: ImmutableSet<Int> = persistentSetOf(10),
    val selectedBakeryPreferences: ImmutableSet<Int> = persistentSetOf(20)
)

@Immutable
internal data class NicknameSettingsState(
    val nicknameText: String = "",
    val descriptionId: Int? = null
)

private val DummyBreadPreferenceList = persistentListOf(
    BreadPreference(id = 1, text = "페이스트리류 (크루아상, 뺑오쇼콜라)"),
    BreadPreference(id = 2, text = "담백한 식사용 빵 (식빵, 치아바타, 바케트, 하드롤)"),
    BreadPreference(id = 3, text = "건강한 빵 (비건, 글루텐프리, 저당)"),
    BreadPreference(id = 4, text = "구움과자 류 (마들렌, 휘낭시에, 까눌레)"),
    BreadPreference(id = 5, text = "클래식 & 레트로 빵 (단팥빵, 맘모스, 꽈배기, 크림빵"),
    BreadPreference(id = 6, text = "달콤한 디저트 빵 (마카롱, 타르트)"),
    BreadPreference(id = 7, text = "달콤한 디저트 빵 (마카롱, 타르트)"),
    BreadPreference(id = 8, text = "샌드위치 / 브런치 스타일"),
    BreadPreference(id = 9, text = "케이크, 브라우니, 파이류"),
)

private val DummyBreadTasteList = persistentListOf(
    BreadPreference(id = 10, text = "달달한게 최고!"),
    BreadPreference(id = 11, text = "버터 풍미 가득한 리치한 맛"),
    BreadPreference(id = 12, text = "짭짤한 빵 (치즈, 햄, 베이컨 등)"),
    BreadPreference(id = 13, text = "담백한 빵 (재료 본연의 맛)"),
    BreadPreference(id = 14, text = "단짠 조합"),
    BreadPreference(id = 15, text = "쫄깃하거나 꾸덕한 식감"),
)

private val DummyBakeryPreferenceList = persistentListOf(
    BreadPreference(id = 16, text = "카페형 빵집 (커피와 함께 머물 수 있는 공간)"),
    BreadPreference(id = 17, text = "전통있는 오래된 빵집"),
    BreadPreference(id = 18, text = "SNS에서 핫한 곳"),
    BreadPreference(id = 19, text = "건강한 재료를 쓰는 수제 빵집"),
    BreadPreference(id = 20, text = "어디든 맛만 좋으면 OK"),
)