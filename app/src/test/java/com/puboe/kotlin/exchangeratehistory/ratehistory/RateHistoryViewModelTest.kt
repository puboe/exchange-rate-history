package com.puboe.kotlin.exchangeratehistory.ratehistory

import com.nhaarman.mockito_kotlin.given
import com.puboe.kotlin.exchangeratehistory.BuildConfig
import com.puboe.kotlin.exchangeratehistory.core.NetworkFailure
import com.puboe.kotlin.exchangeratehistory.core.Success
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.RateHistory
import com.puboe.kotlin.exchangeratehistory.ratehistory.interactor.GetRateHistory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21])
class RateHistoryViewModelTest {

    private lateinit var viewModel: RateHistoryViewModel

    @Mock
    private lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = RateHistoryViewModel(GetRateHistory(dataRepository))
    }

    @Test
    fun loadRateHistorySuccessShouldUpdateLiveData() {
        val rateHistory = RateHistory("EUR", "2019-04-10", "2019-04-15", emptyMap())
        val result = Success(rateHistory)

        given { runBlocking { dataRepository.getRateHistory(anyString(), anyString()) } }.willReturn(result)

        viewModel.rateHistoryLiveData.observeForever {
            with(it) {
                assertEquals(rateHistory.base, base)
                assertEquals(rateHistory.startAt, startAt)
                assertEquals(rateHistory.endAt, endAt)
            }
        }

        runBlocking { viewModel.loadRateHistory("2019-04-10", "2019-04-15") }
    }

    @Test
    fun loadRateHistoryFailureShouldUpdateLiveData() {
        val result = NetworkFailure()

        given { runBlocking { dataRepository.getRateHistory(anyString(), anyString()) } }.willReturn(result)

        viewModel.failureLiveData.observeForever {
            assertEquals(result, it)
        }

        runBlocking { viewModel.loadRateHistory("2019-04-10", "2019-04-15") }
    }
}