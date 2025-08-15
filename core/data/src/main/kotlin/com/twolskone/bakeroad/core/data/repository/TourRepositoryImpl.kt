package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.datastore.AreaEventDataSource
import com.twolskone.bakeroad.core.domain.repository.TourRepository
import com.twolskone.bakeroad.core.model.AreaEvent
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

private val TwelveHoursMillis = TimeUnit.HOURS.toMillis(12) // 43,200,000

internal class TourRepositoryImpl @Inject constructor(
    private val tourDataSource: TourDataSource,
    private val areaEventDataSource: AreaEventDataSource
) : TourRepository {

    override fun getTourAreas(areaCodes: Set<Int>, tourCategories: Set<TourAreaCategory>): Flow<List<TourArea>> {
        return tourDataSource.getAreas(
            areaCodes = areaCodes.joinToString(separator = ","),
            tourCategories = tourCategories.joinToString(separator = ",") { category -> category.code }
        ).map { areas ->
            areas.map { area ->
                area.toExternalModel()
            }
        }
    }

    override fun getAreaEvent(areaCodes: Set<Int>): Flow<AreaEvent> = flow {
        val lastDismissedTimeMillis = areaEventDataSource.getAreaEventLastDismissedTimeMillis()
        val lastApiCallTimeMillis = areaEventDataSource.getAreaEventLastApiCallTimeMillis()
        val startOfToday = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        when {
            // 오늘 안에 "오늘 보지 않기" 선택
            lastDismissedTimeMillis >= startOfToday -> {
                Timber.i("xxx AreaEvent today dismissed")
                return@flow
            }
            // API 마지막 호출 시간이 현재시간 기준, "12시간" 이 넘은 경우
            System.currentTimeMillis() - TwelveHoursMillis >= lastApiCallTimeMillis -> {
                Timber.i("xxx AreaEvent api call")
                emitAll(
                    tourDataSource.getAreaEvent(areaCodes = areaCodes.joinToString(separator = ","))
                        .map { it.toExternalModel() }
                        .onEach { areaEventDataSource.setAreaEvent(value = it) }
                )
            }
            // 이외, 캐시 데이터 활용
            else -> {
                Timber.i("xxx AreaEvent cache")
                areaEventDataSource.getAreaEvent()?.let { areaEvent -> emit(areaEvent) }
            }
        }
    }

    override suspend fun setAreaEventDismissedTimeMillis(timeMillis: Long) {
        areaEventDataSource.setAreaEventLastDismissedTimeMillis(value = timeMillis)
    }
}