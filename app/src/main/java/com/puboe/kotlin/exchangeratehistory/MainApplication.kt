package com.puboe.kotlin.exchangeratehistory

import android.app.Application
import com.puboe.kotlin.exchangeratehistory.di.AppModule
import com.puboe.kotlin.exchangeratehistory.di.ApplicationComponent
import com.puboe.kotlin.exchangeratehistory.di.DaggerApplicationComponent

class MainApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .appModule(AppModule())
            .build()
    }
}