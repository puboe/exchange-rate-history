package com.puboe.kotlin.exchangeratehistory

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.puboe.kotlin.domain.Callback
import com.puboe.kotlin.domain.DataRepository
import com.puboe.kotlin.domain.RateHistory
import com.puboe.kotlin.domain.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetRateHistoryTest {

    private val START_DATE = "2019-04-10"
    private val END_DATE = "2019-04-15"

    private val expected = Success(RateHistory.empty())
    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var useCase: GetRateHistory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetRateHistory(dataRepository)
        given { dataRepository.getRateHistory(START_DATE, END_DATE) }.willReturn(expected)
    }

    @Test
    fun `should get data from repository`() {
        // Set default dispatcher as main so we don't need to mock android's Looper.
        Dispatchers.setMain(Dispatchers.Default)
        var result: Callback? = null
        val onResult = { apiResult: Callback -> result = apiResult }

        runBlocking { useCase(START_DATE, END_DATE, onResult) }

        verify(dataRepository).getRateHistory(START_DATE, END_DATE)
        verifyNoMoreInteractions(dataRepository)
        assertEquals(expected, result)
    }
}