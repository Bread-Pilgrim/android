package com.twolskone.bakeroad.core.common.android.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * 카카오맵
 */
object KakaoMapUtil {

    /**
     * 좌표로 이동
     */
    fun Context.openKakaoMapWithCoordinate(x: Float, y: Float) {
        try {
            if (x > 0f && y > 0f) {
                val url = "kakaomap://look?p=$x,$y"
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(intent)
            }
        } catch (e: Exception) {
            val marketUrl = "market://details?id=net.daum.android.map"
            startActivity(Intent(Intent.ACTION_VIEW, marketUrl.toUri()))
        }
    }
}