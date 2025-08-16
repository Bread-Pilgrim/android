package com.twolskone.bakeroad.core.eventbus

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 메인 탭 간 상태 및 이벤트 관리
 */
interface MainTabEventBus {

    /* 홈 갱신 처리 상태 */
    val homeRefreshState: StateFlow<Boolean>
    fun setHomeRefreshState(value: Boolean)

    /* 홈 재선택 */
    val homeReselectEvent: SharedFlow<Unit>
    suspend fun reselectHome()

    /* 내 빵집 재선택 */
    val myBakeryReselectEvent: SharedFlow<Unit>
    suspend fun reselectMyBakery()
}