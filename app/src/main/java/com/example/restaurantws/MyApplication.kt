package com.example.restaurantws

import android.app.Application
import com.example.restaurantws.core.di.authModule
import com.example.restaurantws.core.di.coreModule
import com.example.restaurantws.core.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    coreModule,
                    mainModule,
                    authModule,
                )
            )
        }
    }
}