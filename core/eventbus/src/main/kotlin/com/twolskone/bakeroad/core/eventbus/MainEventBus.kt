package com.twolskone.bakeroad.core.eventbus

import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 메인화면 상태 및 이벤트 관리
 */
interface MainEventBus {
    /* 홈 탭 갱신 */
    val homeRefreshState: StateFlow<Boolean>
    fun setHomeRefreshState(value: Boolean)

    /* 검색 탭 갱신 */
    val searchRefreshState: StateFlow<Boolean>
    fun setSearchRefreshState(value: Boolean)

    /* 나의 빵글 탭 갱신 */
    val myPageRefreshState: StateFlow<Boolean>
    fun setMyPageRefreshState(value: Boolean)

    /* 토큰 만료 */
    val tokenExpiredEvent: SharedFlow<Unit>
    fun onTokenExpired()

    /* 홈 탭 재선택 */
    val homeReselectEvent: SharedFlow<Unit>
    suspend fun reselectHome()

    /* 내 빵집 탭 재선택 */
    val myBakeryReselectEvent: SharedFlow<Unit>
    suspend fun reselectMyBakery()

    /* 스낵바 */
    val snackbarEvent: SharedFlow<SnackbarState>
    suspend fun showSnackbar(snackbarState: SnackbarState)
}