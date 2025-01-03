package com.example.caronaapp

import android.app.Application
import com.example.caronaapp.di.apiModule
import com.example.caronaapp.di.dataStoreModule
import com.example.caronaapp.di.repositoryModule
import com.example.caronaapp.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                apiModule,
                repositoryModule,
                viewModelModule,
                dataStoreModule
            )
        }
    }
}