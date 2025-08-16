package com.twolskone.bakeroad.di

import com.twolskone.bakeroad.core.eventbus.MainTabEventBus
import com.twolskone.bakeroad.eventbus.MainTabEventBusImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MainTabEventBusModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindsMainTabEventBus(mainTabEventBus: MainTabEventBusImpl): MainTabEventBus
}