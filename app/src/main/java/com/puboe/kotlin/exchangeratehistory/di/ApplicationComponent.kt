package com.puboe.kotlin.exchangeratehistory.di

import com.puboe.kotlin.exchangeratehistory.RateHistoryActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(baseActivity: RateHistoryActivity)
}
