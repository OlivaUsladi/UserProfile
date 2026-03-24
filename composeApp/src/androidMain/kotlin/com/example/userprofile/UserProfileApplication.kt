package com.example.userprofile

import android.app.Application
import com.example.userprofile.di.appModule
import com.example.userprofile.di.dataAppModule
import com.example.userprofile.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UserProfileApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Запускаем Koin
        startKoin {
            androidLogger()
            androidContext(this@UserProfileApplication)
            modules(appModule, domainModule, dataAppModule)
        }
    }
}