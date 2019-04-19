package com.puboe.kotlin.exchangeratehistory

import com.puboe.kotlin.domain.Callback
import com.puboe.kotlin.domain.DataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class GetRateHistory
@Inject constructor(private val dataRepository: DataRepository) {

    private suspend fun run(startDate: String, endDate: String) = dataRepository.getRateHistory(startDate, endDate)

    operator fun invoke(startDate: String, endDate: String, onResult: (Callback) -> Unit = {}) {
        val job = CoroutineScope(Dispatchers.Default).async { run(startDate, endDate) }
        MainScope().launch { onResult(job.await()) }
    }
}
