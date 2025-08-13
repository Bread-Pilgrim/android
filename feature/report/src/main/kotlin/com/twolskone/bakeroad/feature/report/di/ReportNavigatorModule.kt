package com.twolskone.bakeroad.feature.report.di

import com.twolskone.bakeroad.core.navigator.ReportNavigator
import com.twolskone.bakeroad.feature.report.navigator.ReportNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ReportNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsReportNavigator(reportNavigator: ReportNavigatorImpl): ReportNavigator
}