package com.twolskone.bakeroad

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BakeRoadApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKakao()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
        Timber.d("initTimber")
    }

    private fun initKakao() {
        KakaoSdk.init(context = this, appKey = BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}