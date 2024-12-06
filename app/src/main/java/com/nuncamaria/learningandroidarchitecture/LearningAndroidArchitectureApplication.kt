package com.nuncamaria.learningandroidarchitecture

import android.app.Application
import com.nuncamaria.learningandroidarchitecture.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LearningAndroidArchitectureApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LearningAndroidArchitectureApplication)
            androidLogger()
            modules(appModule)
        }
    }
}