package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingConfig

internal const val DefaultPageSize = 15

internal fun defaultPagingConfig(
    pageSize: Int = DefaultPageSize,
    initialLoadSize: Int = pageSize
) = PagingConfig(
    pageSize = pageSize,
    initialLoadSize = initialLoadSize,
//    prefetchDistance = (pageSize / 3).coerceAtLeast(DefaultPageSize / 3),
    enablePlaceholders = true,
)