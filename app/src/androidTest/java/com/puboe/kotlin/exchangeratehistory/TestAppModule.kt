package com.puboe.kotlin.exchangeratehistory

import com.puboe.kotlin.exchangeratehistory.core.di.AppModule
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import dagger.Module

@Module
class TestAppModule(private var repositoryMock: DataRepository) : AppModule() {

    override fun provideDataRepository(): DataRepository {
        return repositoryMock
    }
}
