package com.twolskone.bakeroad.core.eventbus

import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 메인화면 상태 및 이벤트 관리
 */
interface MainEventBus {
    /* 홈 갱신 처리 상태 */
    val homeRefreshState: StateFlow<Boolean>
    fun setHomeRefreshState(value: Boolean)

    /* 홈 재선택 */
    val homeReselectEvent: SharedFlow<Unit>
    suspend fun reselectHome()

    /* 내 빵집 재선택 */
    val myBakeryReselectEvent: SharedFlow<Unit>
    suspend fun reselectMyBakery()

    /* 스낵바 표출 */
    val snackbarEvent: SharedFlow<SnackbarState>
    suspend fun showSnackbar(snackbarState: SnackbarState)
}