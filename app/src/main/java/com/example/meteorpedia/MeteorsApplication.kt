package com.example.meteorpedia

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MeteorsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoinDI()
    }

    private fun initKoinDI(){
        startKoin {
            androidLogger()
            androidContext(this@MeteorsApplication)
            modules(createModuleList())
        }
    }
}