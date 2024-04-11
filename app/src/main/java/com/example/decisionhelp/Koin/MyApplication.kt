package com.example.decisionhelp.Koin

import android.app.Application
import com.example.decisionhelp.Network.NetworkModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication  : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}