package com.twolskone.bakeroad.di

import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.eventbus.MainEventBusImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainEventBusModule {

    @Binds
    @Singleton
    abstract fun bindsMainEventBus(mainEventBus: MainEventBusImpl): MainEventBus
}