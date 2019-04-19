package com.puboe.kotlin.exchangeratehistory.di

import com.puboe.kotlin.domain.DataRepository
import com.puboe.kotlin.domain.MockDataRepository
import com.puboe.kotlin.exchangeratehistory.GetRateHistory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository = MockDataRepository()

    @Provides
    @Singleton
    fun provideGetRateHistory(dataRepository: DataRepository): GetRateHistory = GetRateHistory(dataRepository)
}