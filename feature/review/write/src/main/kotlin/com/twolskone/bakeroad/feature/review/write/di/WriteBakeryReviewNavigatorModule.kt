package com.twolskone.bakeroad.feature.review.write.di

import com.twolskone.bakeroad.core.navigator.WriteBakeryReviewNavigator
import com.twolskone.bakeroad.feature.review.write.navigator.WriteBakeryReviewNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WriteBakeryReviewNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindsWriteBakeryReviewNavigator(writeBakeryReviewNavigator: WriteBakeryReviewNavigatorImpl): WriteBakeryReviewNavigator
}