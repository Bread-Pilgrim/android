package com.twolskone.bakeroad.feature.badge.di

import com.twolskone.bakeroad.core.navigator.BadgeListNavigator
import com.twolskone.bakeroad.feature.badge.navigator.BadgeListNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BadgeListNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsBadgeListNavigator(badgeListNavigator: BadgeListNavigatorImpl): BadgeListNavigator
}