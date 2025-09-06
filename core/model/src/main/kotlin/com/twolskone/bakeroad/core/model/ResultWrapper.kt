package com.twolskone.bakeroad.core.model

/**
 * BaseResponse 내 data 및 extra 값 랩핑 모델
 * (ex. 뱃지 트리거)
 */
data class ResultWrapper<T, R>(
    val data: T,
    val extra: R
)