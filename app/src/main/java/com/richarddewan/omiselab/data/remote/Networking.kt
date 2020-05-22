package com.richarddewan.omiselab.data.remote

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.richarddewan.omiselab.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORK_CALL_TIMEOUT = 60
    private lateinit var okHttpClient: OkHttpClient

    fun create(baseURl: String, cacheDir: File,cacheSize: Long): NetworkService{

        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(cacheDir,cacheSize))
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    }
            )
            .readTimeout(NETWORK_CALL_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseURl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)

    }
}