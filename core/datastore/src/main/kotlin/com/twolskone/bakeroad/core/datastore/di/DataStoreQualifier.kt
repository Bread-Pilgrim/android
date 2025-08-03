package com.twolskone.bakeroad.core.datastore.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class TokenDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class CacheDataStore