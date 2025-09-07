package com.twolskone.bakeroad.core.datastore

import androidx.datastore.core.DataStore
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.datastore.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.AreaEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import com.twolskone.bakeroad.core.datastore.AreaEvent as AreaEventProto

class AreaEventDataSource @Inject constructor(private val areaEventDataStore: DataStore<AreaEventProto>) {

    suspend fun getAreaEvent() = areaEventDataStore.data
        .map { it.toExternalModel() }
        .firstOrNull()

    suspend fun getAreaEventLastDismissedTimeMillis() = areaEventDataStore.data
        .map { it.lastDismissedTimeMillis }
        .firstOrNull()
        .orZero()

    suspend fun getAreaEventLastApiCallTimeMillis() = areaEventDataStore.data
        .map { it.lastApiCallTimeMillis }
        .firstOrNull()
        .orZero()

    suspend fun setAreaEvent(value: AreaEvent) {
        areaEventDataStore.updateData { currentData ->
            currentData.toBuilder()
                .setLastApiCallTimeMillis(System.currentTimeMillis())
                .setTitle(value.title)
                .setAddress(value.address)
                .setStartDate(value.startDate)
                .setEndDate(value.endDate)
                .setImageUrl(value.imageUrl)
                .setLink(value.link)
                .build()
        }
    }

    suspend fun setAreaEventLastDismissedTimeMillis(value: Long) {
        areaEventDataStore.updateData { currentData ->
            currentData.toBuilder()
                .setLastDismissedTimeMillis(value)
                .build()
        }
    }

    suspend fun setAreaEventLastApiCallTimeMillis(value: Long) {
        areaEventDataStore.updateData { currentData ->
            currentData.toBuilder()
                .setLastApiCallTimeMillis(value)
                .build()
        }
    }

    suspend fun clear() {
        areaEventDataStore.updateData { currentData ->
            currentData.toBuilder()
                .clear()
                .build()
        }
    }
}