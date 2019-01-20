package com.kotlinlib.activity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class KotlinApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: KotlinApplication
    }

    private lateinit var _context: Context

    init {

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        _context = applicationContext
        initOKHttp()
    }

    @Synchronized
    fun context(): KotlinApplication {
        return _context as KotlinApplication
    }

    private fun initOKHttp() {
        val okHttpClient = OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build()
        OkHttpUtils.initClient(okHttpClient)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}