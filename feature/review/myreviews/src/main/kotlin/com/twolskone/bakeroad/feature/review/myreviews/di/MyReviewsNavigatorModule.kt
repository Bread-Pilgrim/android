package com.twolskone.bakeroad.feature.review.myreviews.di

import com.twolskone.bakeroad.core.navigator.MyReviewsNavigator
import com.twolskone.bakeroad.feature.review.myreviews.navigator.MyReviewsNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MyReviewsNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsMyReviewsNavigator(myReviewsNavigator: MyReviewsNavigatorImpl): MyReviewsNavigator
}