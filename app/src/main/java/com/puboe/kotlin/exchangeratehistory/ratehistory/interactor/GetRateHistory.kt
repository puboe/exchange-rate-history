package com.puboe.kotlin.exchangeratehistory.ratehistory.interactor

import com.puboe.kotlin.exchangeratehistory.core.Callback
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class GetRateHistory
@Inject constructor(private val dataRepository: DataRepository) {

    private suspend fun run(startDate: String, endDate: String) = dataRepository.getRateHistory(startDate, endDate)

    operator fun invoke(startDate: String, endDate: String, onResult: (com.puboe.kotlin.exchangeratehistory.core.Callback) -> Unit = {}) {
        val job = CoroutineScope(Dispatchers.Default).async { run(startDate, endDate) }
        MainScope().launch { onResult(job.await()) }
    }
}
