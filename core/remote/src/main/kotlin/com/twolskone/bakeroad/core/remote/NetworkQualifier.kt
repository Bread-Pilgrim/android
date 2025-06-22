package com.twolskone.bakeroad.core.remote

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class CommonOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class CommonRetrofit
