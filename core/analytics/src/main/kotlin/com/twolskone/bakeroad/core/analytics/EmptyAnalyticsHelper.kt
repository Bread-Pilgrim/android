package com.twolskone.bakeroad.core.analytics

internal class EmptyAnalyticsHelper : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) = Unit
}