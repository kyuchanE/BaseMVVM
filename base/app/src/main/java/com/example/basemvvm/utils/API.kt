package com.example.basemvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.example.basemvvm.BuildConfig
import com.example.basemvvm.custom.lib.CustomPersistentCookieJar
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object API {

    private const val PRINT_LOG = BuildConfig.DEV // 로그 출력여부

    private const val CONNECT_TIMEOUT = 3000L // 커넥션 타임
    private const val WRITE_TIMEOUT = 3000L // 쓰기 타임
    private const val READ_TIMEOUT = 3000L // 읽기 타임

    private val BASE_URL = BuildConfig.BASE_URL // API URL

    lateinit var context: Context

    val okHttpClient by lazy {
        val okHttpClientBuilder = configureClient(OkHttpClient().newBuilder())!!
//        val okHttpClientBuilder = OkHttpClient.Builder()

        val cookieJar = CustomPersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        okHttpClientBuilder
//                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS)) // https 관련 보안 옵션
                .cookieJar(cookieJar)                               // 쿠키 저장
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 쓰기 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)      // 읽기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)        // 연결 타임아웃 시간 설정
                .cache(null)                                 // 캐시사용 안함
                .addInterceptor { chain ->
                    chain.proceed(
                            chain.request()
                                    .newBuilder()
                                    .header("User-Agent", "")
                                    .header("devicemodel", Build.MODEL)
//                                    .header("key", "value")
                                    .build()
                    )
                }

        if (PRINT_LOG) {
            val httpLoggingInterceptor = LoggingInterceptor.Builder() // 전송로그
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .tag("ohlog")
                    .build()

            okHttpClientBuilder
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(OkHttpProfilerInterceptor())
                    .addNetworkInterceptor(StethoInterceptor()) // Stetho 로그
        }

        okHttpClientBuilder.build()
    }

//    val basicApi by lazy { build().create(BasicApi::class.java) }

    fun init(ctx: Context) {
        context = ctx
    }

    /**
     * 기본 설정하여 Retrofit을 반환
     *
     * @return 설정이 반영된 Retrofit
     */
    fun build(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // Rx를 사용할 수 있도록 아답터 적용
                .addConverterFactory(ScalarsConverterFactory.create())      // ScalarConverter 적용
                .addConverterFactory(GsonConverterFactory.create())         // GsonConverter 적용
                .build()
    }

}

/**
 * UnCertificated 허용
 *
 * @param builder OkHttpClient builder
 * @return 인증서를 무시하는 OkHttpClient builder
 */
@SuppressLint("TrustAllX509TrustManager")
fun configureClient(builder: OkHttpClient.Builder): OkHttpClient.Builder? {
    val certs = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }


        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }
    )
    var ctx: SSLContext? = null
    try {
        ctx = SSLContext.getInstance("TLS")
        ctx.init(null, certs, SecureRandom())
    } catch (ex: Exception) {
        L.e(ex)
    }
    if (ctx.notNull) {
        try {
            val trustManager = certs[0] as X509TrustManager
            val sslSocketFactory = ctx!!.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(
                    HostnameVerifier { _, _ -> true })
        } catch (e: Exception) {
            L.e(e)
        }
    }
    return builder
}