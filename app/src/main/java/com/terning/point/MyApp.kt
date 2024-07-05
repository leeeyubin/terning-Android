package com.terning.point

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import com.kakao.sdk.common.KakaoSdk

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        setDayMode()
        initKakoSdk()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initKakoSdk(){
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}