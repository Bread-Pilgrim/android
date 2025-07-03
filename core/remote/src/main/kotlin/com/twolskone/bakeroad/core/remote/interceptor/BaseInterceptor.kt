package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.common.kotlin.network.extension.handleNetworkException
import com.twolskone.bakeroad.core.datastore.TokenDataSource
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import timber.log.Timber

internal open class BaseInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val response = chain.proceed(chain.request())
            val responseBody = response.body

            if (response.isSuccessful && responseBody != null) {
                val responseBodyString = responseBody.string()
                val json = JSONObject(responseBodyString)
                // Update expired token.
                if (json.has("token")) {
                    json.optJSONObject("token")?.let { token ->
                        Timber.i("Update expired token..")
                        if (token.has("access_token") && token.has("refresh_token")) {
                            runBlocking {
                                tokenDataSource.setTokens(
                                    accessToken = token.getString("access_token"),
                                    refreshToken = token.getString("refresh_token")
                                )
                            }
                        }
                    }
                }

                return response.newBuilder()
                    .body(responseBodyString.toResponseBody(responseBody.contentType()))
                    .build()
            }

            response
        } catch (e: Exception) {
            e.handleNetworkException()
        }
    }
}