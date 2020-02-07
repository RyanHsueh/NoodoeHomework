package com.ryanhsueh.noodoehomeword.api

import com.ryanhsueh.noodoehomeword.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val HTTP_CLIENT_MODULE_TAG = "httpClientModule"

const val TIME_OUT_SECONDS = 20

val httpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with singleton { OkHttpClient.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()   // 委托给了bind<Retrofit.Builder>()函数
            .baseUrl(Constants.BASE_URL)
            .client(instance())    // 委托给了 bind<OkHttpClient>() 函数
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()  // 委托给bind<OkHttpClient.Builder>()函数
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS)
            .readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS)
            .addInterceptor(AddHeaderInterceptor())
            .build()
    }

}

private class AddHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.header(
            "X-Parse-Application-Id",
            Constants.APPLICATION_ID
        )
        builder.header(
            "X-Parse-REST-API-Key",
            Constants.REST_API_KEY
        )
        if (HomeworkService.sessionToken != null) {
            builder.header(
                "X-Parse-Session-Token",
                HomeworkService.sessionToken!!
            )
        }

        return chain.proceed(builder.build())
    }

}