package com.twolskone.bakeroad.feature.bakery.detail.di

import com.twolskone.bakeroad.core.navigator.BakeryDetailNavigator
import com.twolskone.bakeroad.feature.bakery.detail.navigator.BakeryDetailNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BakeryDetailNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsBakeryDetailNavigator(bakeryDetailNavigator: BakeryDetailNavigatorImpl): BakeryDetailNavigator
}