package com.example.basemvvm.base

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.example.basemvvm.BuildConfig
import com.example.basemvvm.ex_koin.moduleA
import com.example.basemvvm.ex_koin.moduleB
import com.example.basemvvm.ex_koin.typeQualifierModule
import com.example.basemvvm.utils.API
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import es.dmoral.toasty.Toasty
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    var mainActivityLive = false

    override fun onCreate() {
        super.onCreate()

        // 유틸
        Utils.init(this)

        // 프리퍼런스
        Hawk.init(this).build()

        // 통신모듈
        API.init(this)

        // debug
        Stetho.initializeWithDefaults(this)

        // 웹뷰 디버깅
        if (BuildConfig.DEV && Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        // Toast
        Toasty.Config.getInstance()
            .allowQueue(false) // optional (prevents several Toastys from queuing)
            .apply()

        // Koin
        startKoin {
            androidContext(this@BaseApplication)

            // 사용할 Module 등록
            // 복수개(여러개) 모듈 등록 시
            // modules(a_Module, b_Module, c_Module)
            //
            // moduleA는 ComponentA 객체를 생성하고 ModuleB는 ComponentB 객체를 생성하는데 ComponentA 객체가 필요
            // 2개의 Module 을 모두 사용등록을 하면 ComponentB 객체 요쳥 시 Koin 은 ModuleB에게 ModuleA를 통해 ComponentA 객체를 주입해주고 ComponentB 객체를 생성하여 의존성 주입을 완료합니다
            // 이렇게 가능한 이유는 Koin 의 지연초기화 방식으로 Module 등록 시 인스턴스가 즉시 생성되는게 아닌, 요청 시 생성하므로 여러 Module 들을 순회하며 서로 상호운용이 가능합니다
            modules(
                    moduleA,
                    moduleB,
                    typeQualifierModule
            )
        }
    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
        TODO("Not yet implemented")
    }
}