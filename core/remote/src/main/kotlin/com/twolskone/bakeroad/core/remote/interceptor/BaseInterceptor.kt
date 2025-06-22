package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.common.kotlin.network.extension.handleNetworkException
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

internal open class BaseInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            e.handleNetworkException()
        }
    }
}