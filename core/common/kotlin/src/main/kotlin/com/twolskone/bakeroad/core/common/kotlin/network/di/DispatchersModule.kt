package com.twolskone.bakeroad.core.common.kotlin.network.di

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(BakeRoadDispatcher.DEFAULT)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(BakeRoadDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}