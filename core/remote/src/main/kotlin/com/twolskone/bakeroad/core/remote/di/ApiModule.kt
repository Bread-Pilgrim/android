package com.twolskone.bakeroad.core.remote.di

import com.twolskone.bakeroad.core.remote.AuthRetrofit
import com.twolskone.bakeroad.core.remote.CommonRetrofit
import com.twolskone.bakeroad.core.remote.api.AuthApi
import com.twolskone.bakeroad.core.remote.api.PreferApi
import com.twolskone.bakeroad.core.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun providesAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi = retrofit.create()

    @Provides
    @Singleton
    fun providesPreferApi(@CommonRetrofit retrofit: Retrofit): PreferApi = retrofit.create()

    @Provides
    @Singleton
    fun providesUserApi(@CommonRetrofit retrofit: Retrofit): UserApi = retrofit.create()
}