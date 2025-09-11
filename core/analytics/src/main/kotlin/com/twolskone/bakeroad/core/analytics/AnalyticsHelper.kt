package com.twolskone.bakeroad.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject

interface AnalyticsHelper {
    fun logEvent(event: AnalyticsEvent)
}

internal class AnalyticsHelperImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            event.extras.forEach { extra ->
                param(
                    key = extra.key.take(40),
                    value = extra.value.take(100)
                )
            }
        }
    }
}