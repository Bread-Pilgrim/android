package com.twolskone.bakeroad.core.remote.interceptor

import com.twolskone.bakeroad.core.datastore.TokenDataSource
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber

/**
 * Token Interceptor
 * Add JWT access token.
 */
internal class TokenInterceptor @Inject constructor(private val tokenDataSource: TokenDataSource) : BaseInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (accessToken, refreshToken) = runBlocking { tokenDataSource.getTokens() }
        val request = chain.request().newBuilder()
            .addHeader("access-token", accessToken)
            .addHeader("refresh-token", refreshToken)
            .build()
        val response = chain.proceed(request)
        val responseBody = response.body

        if (response.isSuccessful && responseBody != null) {
            val json = JSONObject(responseBody.string())

            // Update expired token.
            if (json.has("token")) {
                Timber.i("Update expired token..")
                json.optJSONObject("token")?.let { token ->
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
        }

        return response
    }
}