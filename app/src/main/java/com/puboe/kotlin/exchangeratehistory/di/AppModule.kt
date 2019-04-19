package com.puboe.kotlin.exchangeratehistory.di

import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.domain.ApiDataRepository
import com.puboe.kotlin.domain.DataRepository
import com.puboe.kotlin.exchangeratehistory.GetRateHistory
import com.puboe.kotlin.exchangeratehistory.RateHistoryViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository = ApiDataRepository()

    @Provides
    @Singleton
    fun provideGetRateHistory(dataRepository: DataRepository): GetRateHistory = GetRateHistory(dataRepository)

    @Provides
    @Singleton
    fun provideRateHistoryViewModelFactory(useCase: GetRateHistory): ViewModelProvider.Factory =
        RateHistoryViewModel.Factory(useCase)
}