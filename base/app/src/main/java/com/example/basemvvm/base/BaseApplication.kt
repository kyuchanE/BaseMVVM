package com.example.basemvvm.base

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.webkit.WebView
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.example.basemvvm.BuildConfig
import com.example.basemvvm.ex_koin.*
import com.example.basemvvm.utils.API
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import es.dmoral.toasty.Toasty
import org.greenrobot.eventbus.Subscribe
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class BaseApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
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


        // 비정상 종료시 앱 로그 남기기
//        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
    }

    // 이벤트 버스 버그를 막기위한 메소드
    // greenrobot 이벤트 버스는 기본적으로 Subscribe 메소드가 하나라도 작성되어있어야 함
    @Subscribe
    fun eventbus(baseApplication: BaseApplication) {
    }

    // 메모리 부족 현상 이슈 대응
    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    // 메모리 부족 현상 이슈 대응
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }

//    inner class ExceptionHandler : Thread.UncaughtExceptionHandler {
//        override fun uncaughtException(t: Thread, e: Throwable) {
//            // 비정상 종료시 로그
//
//            var fileName =
//                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/LG_ThinQ_RetailMode/log/"
//
//            val folder = File(fileName)
//            folder.mkdirs()
//            fileName = fileName + "ErrorLog.txt"
//
//            val yyyyMMddHHmmssFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", Locale.KOREA)
//            val logFile: File = File(fileName)
//            if (!logFile.exists()) {
//                try {
//                    logFile.createNewFile()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//            try {
//                var writer: StringWriter? = StringWriter()
//                e.printStackTrace(PrintWriter(writer!!))
//
//                val bufWrt = BufferedWriter(FileWriter(logFile, true))
//                bufWrt.append(yyyyMMddHHmmssFormat.format(Date()))
//                bufWrt.newLine()
//                bufWrt.append(writer.toString())
//                bufWrt.newLine()
//                bufWrt.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            android.os.Process.killProcess(android.os.Process.myPid());
//            exitProcess(10);
//        }
//    }

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