package com.twolskone.bakeroad.core.remote.di

import com.twolskone.bakeroad.core.remote.AuthOkHttpClient
import com.twolskone.bakeroad.core.remote.AuthRetrofit
import com.twolskone.bakeroad.core.remote.BuildConfig
import com.twolskone.bakeroad.core.remote.CommonOkHttpClient
import com.twolskone.bakeroad.core.remote.CommonRetrofit
import com.twolskone.bakeroad.core.remote.interceptor.BaseInterceptor
import com.twolskone.bakeroad.core.remote.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L
    private const val BASE_URL = BuildConfig.BASE_URL
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @AuthOkHttpClient
    @Provides
    @Singleton
    fun providesAuthOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, baseInterceptor: BaseInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(baseInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @CommonOkHttpClient
    @Provides
    @Singleton
    fun providesCommonOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, tokenInterceptor: TokenInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @AuthRetrofit
    @Provides
    @Singleton
    fun providesAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @CommonRetrofit
    @Provides
    @Singleton
    fun providesCommonRetrofit(@CommonOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}