package com.twolskone.bakeroad.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_DATASTORE = "token"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE)

private const val CACHE_DATA_SOURCE = "cache"
private val Context.cacheDataSource: DataStore<Preferences> by preferencesDataStore(name = CACHE_DATA_SOURCE)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    @TokenDataStore
    fun providesTokenDataStore(@ApplicationContext context: Context) = context.tokenDataStore

    @Provides
    @Singleton
    @CacheDataStore
    fun providesCacheDataStore(@ApplicationContext context: Context) = context.cacheDataSource
}