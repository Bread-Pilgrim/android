package com.twolskone.bakeroad.feature.settings.main.di

import com.twolskone.bakeroad.core.navigator.SettingsNavigator
import com.twolskone.bakeroad.feature.settings.main.navigator.SettingsNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsSettingsNavigator(settingsNavigator: SettingsNavigatorImpl): SettingsNavigator
}