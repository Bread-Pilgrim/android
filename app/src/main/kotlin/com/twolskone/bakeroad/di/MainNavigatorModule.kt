package com.twolskone.bakeroad.di

import com.twolskone.bakeroad.core.navigator.MainNavigator
import com.twolskone.bakeroad.navigator.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsMainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator
}