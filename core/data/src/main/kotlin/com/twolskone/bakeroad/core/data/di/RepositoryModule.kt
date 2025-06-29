package com.twolskone.bakeroad.core.data.di

import com.twolskone.bakeroad.core.data.repository.AuthRepositoryImpl
import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}