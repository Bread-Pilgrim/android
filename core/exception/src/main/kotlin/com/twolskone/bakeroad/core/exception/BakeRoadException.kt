package com.twolskone.bakeroad.core.exception

/**
 * 서버 에러
 * @param statusCode    상태 코드
 * @param error         커스텀 에러
 */
class BakeRoadException(
    val statusCode: Int,
    val error: BakeRoadError? = null,
    override val message: String
) : BaseException(message) {

    companion object {
        const val STATUS_CODE_UNKNOWN = 1000            // 알 수 없는 오류
        const val STATUS_CODE_INVALID_TOKEN = 1001      // 토큰 만료 또는 유효하지 않은 토큰
        const val STATUS_CODE_MISSING_REQUEST = 1002    // 요청 누락
        const val STATUS_CODE_NOT_FOUND = 1004          // 데이터를 찾을 수 없음
        const val STATUS_CODE_INVALID_AREA_CODE = 1005  // 잘못된 지역코드 요청
        const val STATUS_CODE_INVALID_SORT_DATA = 1006  // 잘못된 정렬 데이터 요청
        const val STATUS_CODE_DUPLICATE_DATA = 1009     // 중복 데이터
    }
}

enum class BakeRoadError(val code: String) {
    DuplicateNickname(code = "DUPLICATE_NICKNAME"), // 닉네임 중복 에러
    AlreadyOnboarding(code = "ALREADY_ONBOARDED");  // 온보딩에서 이미 취향설정해서 발생하는 에러

    companion object {
        fun ofCode(code: String): BakeRoadError? = entries.find { it.code == code }
    }
}