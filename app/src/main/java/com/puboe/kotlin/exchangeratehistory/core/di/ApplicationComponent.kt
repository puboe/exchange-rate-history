package com.puboe.kotlin.exchangeratehistory.core.di

import com.puboe.kotlin.exchangeratehistory.ratehistory.RateHistoryActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(activity: RateHistoryActivity)
}
