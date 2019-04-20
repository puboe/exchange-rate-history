package com.puboe.kotlin.exchangeratehistory

import android.app.Application
import com.puboe.kotlin.exchangeratehistory.core.di.AppModule
import com.puboe.kotlin.exchangeratehistory.core.di.ApplicationComponent
import com.puboe.kotlin.exchangeratehistory.core.di.DaggerApplicationComponent

class MainApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .appModule(AppModule())
            .build()
    }
}