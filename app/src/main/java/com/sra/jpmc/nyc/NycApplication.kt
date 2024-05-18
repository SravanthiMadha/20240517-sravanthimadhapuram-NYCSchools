package com.sra.jpmc.nyc

import android.app.Application
import com.sra.jpmc.nyc.di.networkModule
import com.sra.jpmc.nyc.di.repositoryModule
import com.sra.jpmc.nyc.di.servicesModule
import com.sra.jpmc.nyc.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class NycApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NycApplication)
            modules(
                viewModelModule,
                servicesModule,
                repositoryModule,
                networkModule
            )
        }
    }
}