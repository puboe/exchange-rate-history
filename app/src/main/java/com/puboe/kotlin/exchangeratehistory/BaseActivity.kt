package com.puboe.kotlin.exchangeratehistory

import androidx.appcompat.app.AppCompatActivity
import com.puboe.kotlin.exchangeratehistory.di.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    internal val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as MainApplication).appComponent
    }
}