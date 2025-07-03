package com.twolskone.bakeroad.feature.onboard.di

import com.twolskone.bakeroad.core.navigator.OnboardingNavigator
import com.twolskone.bakeroad.feature.onboard.navigator.OnboardingNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OnboardingNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsOnboardingNavigator(onboardingNavigator: OnboardingNavigatorImpl): OnboardingNavigator
}