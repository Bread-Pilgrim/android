package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.datastore.TokenDataSource
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Token Interceptor
 * Add JWT access token.
 */
internal class TokenInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : BaseInterceptor(tokenDataSource) {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (accessToken, refreshToken) = runBlocking { tokenDataSource.getTokens() }
        val request = chain.request().newBuilder()
            .addHeader("access-token", accessToken)
            .addHeader("refresh-token", refreshToken)
            .build()

        return chain.proceed(request)
    }
}