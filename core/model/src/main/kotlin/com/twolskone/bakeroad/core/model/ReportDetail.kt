package com.twolskone.bakeroad.core.model

import com.twolskone.bakeroad.core.model.type.DayOfWeek

/**
 * 빵말정산
 * @property year                       연도
 * @property month                      월
 * @property visitedAreas               방문한 지역 (지역명:횟수)
 * @property breadTypes                 빵 타입 (빵이름:구매횟수)
 * @property breadDailyAverageCount     빵 하루평균 소비량
 * @property breadMonthlyConsumptionGap 전체 사용자의 빵 소비량 차이
 * @property totalBreadCount            총 빵 소비량
 * @property totalVisitedCount          총 방문횟수
 * @property totalPrices                총 빵 구매금액
 * @property breadWeeklyDistribution    요일별 빵 소비량 (요일:소비횟수)
 * @property reviewCount                리뷰 개수
 * @property sentLikeCount              보낸 좋아요 개수
 * @property receivedLikeCount          받은 좋아요 개수
 */
data class ReportDetail(
    val year: Int,
    val month: Int,
    val visitedAreas: List<Pair<String, Int>>,
    val breadTypes: List<Pair<String, Int>>,
    val breadDailyAverageCount: Float,
    val breadMonthlyConsumptionGap: Float,
    val totalBreadCount: Int,
    val totalVisitedCount: Int,
    val totalPrices: List<Int>,
    val breadWeeklyDistribution: Map<DayOfWeek, Int>,
    val reviewCount: Int,
    val sentLikeCount: Int,
    val receivedLikeCount: Int
) {

    companion object {
        fun ofEmpty() = ReportDetail(
            year = 0,
            month = 0,
            visitedAreas = emptyList(),
            breadTypes = emptyList(),
            breadDailyAverageCount = 0f,
            breadMonthlyConsumptionGap = 0f,
            totalBreadCount = 0,
            totalVisitedCount = 0,
            totalPrices = emptyList(),
            breadWeeklyDistribution = DayOfWeek.entries.associateWith { 0 },
            reviewCount = 0,
            sentLikeCount = 0,
            receivedLikeCount = 0
        )
    }
}