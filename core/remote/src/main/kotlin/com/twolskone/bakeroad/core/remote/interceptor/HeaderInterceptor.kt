package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.datastore.TokenDataSource
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Header Interceptor
 * Add JWT access token and refresh token.
 */
internal class HeaderInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (accessToken, refreshToken) = runBlocking { tokenDataSource.getTokens() }
        val request = chain.request().newBuilder()
            .addHeader("access-token", accessToken)
            .addHeader("refresh-token", refreshToken)
            .build()

        return chain.proceed(request)
    }
}