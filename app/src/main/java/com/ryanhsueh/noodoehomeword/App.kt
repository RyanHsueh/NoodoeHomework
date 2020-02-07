package com.ryanhsueh.noodoehomeword

import android.app.Application
import com.ryanhsueh.noodoehomeword.api.httpClientModule
import com.ryanhsueh.noodoehomeword.api.serviceModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(httpClientModule)
        import(serviceModule)
    }

}