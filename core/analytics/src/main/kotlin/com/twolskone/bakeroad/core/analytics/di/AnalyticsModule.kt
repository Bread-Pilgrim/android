package com.twolskone.bakeroad.core.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.twolskone.bakeroad.core.analytics.AnalyticsHelper
import com.twolskone.bakeroad.core.analytics.AnalyticsHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AnalyticsModule {

    companion object {

        @Provides
        @Singleton
        fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
    }

    @Binds
    @Singleton
    abstract fun bindsAnalyticsHelper(analyticsHelper: AnalyticsHelperImpl): AnalyticsHelper
}