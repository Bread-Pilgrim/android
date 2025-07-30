package com.twolskone.bakeroad.core.remote.di

import com.twolskone.bakeroad.core.remote.datasource.AreaDataSource
import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.datasource.PreferDataSource
import com.twolskone.bakeroad.core.remote.datasource.ReviewDataSource
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.datasource.impl.AreaDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.AuthDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.BakeryDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.PreferDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.ReviewDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.TourDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindsPreferDataSource(preferDataSource: PreferDataSourceImpl): PreferDataSource

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(userDataSource: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindsTourDataSource(tourDataSource: TourDataSourceImpl): TourDataSource

    @Binds
    @Singleton
    abstract fun bindsAreaDataSource(areaDataSource: AreaDataSourceImpl): AreaDataSource

    @Binds
    @Singleton
    abstract fun bindsBakeryDataSource(bakeryDataSource: BakeryDataSourceImpl): BakeryDataSource

    @Binds
    @Singleton
    abstract fun bindsReviewDataSource(reviewDataSource: ReviewDataSourceImpl): ReviewDataSource
}