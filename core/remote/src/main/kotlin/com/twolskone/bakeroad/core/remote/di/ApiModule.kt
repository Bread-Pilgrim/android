package com.twolskone.bakeroad.core.remote.di

import com.twolskone.bakeroad.core.remote.api.AreaApi
import com.twolskone.bakeroad.core.remote.api.AuthApi
import com.twolskone.bakeroad.core.remote.api.BadgeApi
import com.twolskone.bakeroad.core.remote.api.BakeryApi
import com.twolskone.bakeroad.core.remote.api.NoticeApi
import com.twolskone.bakeroad.core.remote.api.PreferenceApi
import com.twolskone.bakeroad.core.remote.api.ReviewApi
import com.twolskone.bakeroad.core.remote.api.SearchApi
import com.twolskone.bakeroad.core.remote.api.TourApi
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
    fun providesPreferApi(@CommonRetrofit retrofit: Retrofit): PreferenceApi = retrofit.create()

    @Provides
    @Singleton
    fun providesUserApi(@CommonRetrofit retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun providesTourApi(@CommonRetrofit retrofit: Retrofit): TourApi = retrofit.create()

    @Provides
    @Singleton
    fun providesAreaApi(@CommonRetrofit retrofit: Retrofit): AreaApi = retrofit.create()

    @Provides
    @Singleton
    fun providesBakeryApi(@CommonRetrofit retrofit: Retrofit): BakeryApi = retrofit.create()

    @Provides
    @Singleton
    fun providesReviewApi(@CommonRetrofit retrofit: Retrofit): ReviewApi = retrofit.create()

    @Provides
    @Singleton
    fun providesSearchApi(@CommonRetrofit retrofit: Retrofit): SearchApi = retrofit.create()

    @Provides
    @Singleton
    fun providesBadgeApi(@CommonRetrofit retrofit: Retrofit): BadgeApi = retrofit.create()

    @Provides
    @Singleton
    fun providesNoticeApi(@CommonRetrofit retrofit: Retrofit): NoticeApi = retrofit.create()
}