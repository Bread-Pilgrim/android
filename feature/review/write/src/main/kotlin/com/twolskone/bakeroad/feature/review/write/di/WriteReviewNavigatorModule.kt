package com.twolskone.bakeroad.feature.review.write.di

import com.twolskone.bakeroad.core.navigator.WriteReviewNavigator
import com.twolskone.bakeroad.feature.review.write.navigator.WriteReviewNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WriteReviewNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindsWriteReviewNavigator(writeReviewNavigator: WriteReviewNavigatorImpl): WriteReviewNavigator
}