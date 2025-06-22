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
internal class TokenInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : BaseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenDataSource.getAccessToken() }
        val request = chain.request().newBuilder()
            .addHeader("access-token", accessToken)
            .build()

        return chain.proceed(request)
    }
}