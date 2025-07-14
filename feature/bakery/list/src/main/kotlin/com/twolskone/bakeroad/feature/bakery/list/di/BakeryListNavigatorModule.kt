package com.twolskone.bakeroad.feature.bakery.list.di

import com.twolskone.bakeroad.core.navigator.BakeryListNavigator
import com.twolskone.bakeroad.feature.bakery.list.navigator.BakeryListNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BakeryListNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsBakeryListNavigator(bakeryListNavigator: BakeryListNavigatorImpl): BakeryListNavigator
}