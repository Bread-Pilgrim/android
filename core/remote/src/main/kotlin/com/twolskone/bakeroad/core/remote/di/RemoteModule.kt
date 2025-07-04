package com.twolskone.bakeroad.core.remote.di

import com.twolskone.bakeroad.core.remote.datasource.AuthDataSource
import com.twolskone.bakeroad.core.remote.datasource.PreferDataSource
import com.twolskone.bakeroad.core.remote.datasource.UserDataSource
import com.twolskone.bakeroad.core.remote.datasource.impl.AuthDataSourceImpl
import com.twolskone.bakeroad.core.remote.datasource.impl.PreferDataSourceImpl
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
    abstract fun bindsUserDataSource(userDataSource: UserDataSourceImpl): UserDataSource
}