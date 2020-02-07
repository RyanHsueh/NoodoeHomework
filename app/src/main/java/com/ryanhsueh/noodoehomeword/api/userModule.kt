package com.ryanhsueh.noodoehomeword.api

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

const val SERVICE_MODULE_TAG = "serviceModule"

val serviceModule = Kodein.Module(SERVICE_MODULE_TAG) {

    bind<UserService>() with singleton {
        // Retrofit对象的获取已经在httpClientModule中声明好了
        instance<Retrofit>().create(UserService::class.java)
    }

    bind<ServiceManager>() with singleton {
        ServiceManager(instance())  // userService的获取方式已经声明
    }
}

// 目前ServiceManager只有User相关的API接口，可后续慢慢追加
data class ServiceManager(val userService: UserService)