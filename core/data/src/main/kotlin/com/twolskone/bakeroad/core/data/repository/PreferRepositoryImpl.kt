package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.PreferRepository
import com.twolskone.bakeroad.core.model.PreferenceOptions
import com.twolskone.bakeroad.core.remote.datasource.PreferDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferRepositoryImpl @Inject constructor(
    private val preferDataSource: PreferDataSource,
) : PreferRepository {

    override fun getOptions(): Flow<PreferenceOptions> {
        return preferDataSource.getOptions()
            .map { options -> options.toExternalModel() }
    }
}