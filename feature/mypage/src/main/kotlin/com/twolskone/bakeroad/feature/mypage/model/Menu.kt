package com.twolskone.bakeroad.feature.mypage.model

import androidx.annotation.StringRes
import com.twolskone.bakeroad.feature.mypage.R
import com.twolskone.bakeroad.feature.mypage.model.Menu.Badge
import com.twolskone.bakeroad.feature.mypage.model.Menu.Preference
import com.twolskone.bakeroad.feature.mypage.model.Menu.Review
import com.twolskone.bakeroad.feature.mypage.model.Menu.Settlement

/**
 * 나의 빵글 메뉴
 * @property Settlement 빵말정산
 * @property Badge      받은 뱃지
 * @property Review     내가 쓴 리뷰
 * @property Preference 취향 변경
 */
internal enum class Menu(@StringRes val labelRes: Int) {
    Settlement(labelRes = R.string.feature_mypage_bakeroad_settlement),
    Badge(labelRes = R.string.feature_mypage_bakeroad_received_badge),
    Review(labelRes = R.string.feature_mypage_bakeroad_my_review),
    Preference(labelRes = R.string.feature_mypage_bakeroad_change_preference)
}