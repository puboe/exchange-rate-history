package com.puboe.kotlin.exchangeratehistory.core.di

import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.exchangeratehistory.ratehistory.RateHistoryViewModel
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.network.ApiDataRepository
import com.puboe.kotlin.exchangeratehistory.ratehistory.interactor.GetRateHistory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideDataRepository(): DataRepository = ApiDataRepository()

    @Provides
    @Singleton
    fun provideGetRateHistory(dataRepository: DataRepository): GetRateHistory = GetRateHistory(dataRepository)

    @Provides
    @Singleton
    fun provideRateHistoryViewModelFactory(useCase: GetRateHistory): ViewModelProvider.Factory =
        RateHistoryViewModel.Factory(useCase)
}