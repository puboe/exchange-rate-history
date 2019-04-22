package com.puboe.kotlin.exchangeratehistory

import android.app.Application
import com.puboe.kotlin.exchangeratehistory.core.di.AppModule
import com.puboe.kotlin.exchangeratehistory.core.di.ApplicationComponent
import com.puboe.kotlin.exchangeratehistory.core.di.DaggerApplicationComponent

class MainApplication : Application() {

    var appComponent: ApplicationComponent =
        DaggerApplicationComponent
            .builder()
            .appModule(AppModule())
            .build()
}