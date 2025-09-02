package com.twolskone.bakeroad.feature.intro.di

import com.twolskone.bakeroad.core.navigator.IntroNavigator
import com.twolskone.bakeroad.feature.intro.navigator.IntroNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class IntroNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsIntroNavigator(introNavigator: IntroNavigatorImpl): IntroNavigator
}