package com.el3asas.utils

import android.app.Application
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}