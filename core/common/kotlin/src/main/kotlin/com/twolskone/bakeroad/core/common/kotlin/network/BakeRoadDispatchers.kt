package com.twolskone.bakeroad.core.common.kotlin.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val bakeRoadDispatcher: BakeRoadDispatcher)

enum class BakeRoadDispatcher {
    DEFAULT,
    IO
}