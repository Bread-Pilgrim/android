package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.datastore.TokenDataSource
import com.twolskone.bakeroad.core.exception.extension.handleNetworkException
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import timber.log.Timber

internal class ResponseInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val request = chain.request()
            val response = chain.proceed(request)
            val responseBody = response.body

            if (responseBody != null) {
                val responseBodyString = responseBody.string()
                val json = JSONObject(responseBodyString)
                val responseBuilder = if (response.isSuccessful) {
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
                    response.newBuilder()
                } else {
                    // Handling as a server custom error. (status code)
                    Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message(json.optString("message").orEmpty())
                }

                return responseBuilder
                    .body(responseBodyString.toResponseBody(responseBody.contentType()))
                    .build()
            }

            response
        } catch (e: Exception) {
            e.handleNetworkException()
        }
    }
}