package com.puboe.kotlin.exchangeratehistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.domain.Failure
import com.puboe.kotlin.domain.RateHistory
import com.puboe.kotlin.domain.Success
import javax.inject.Inject

class RateHistoryViewModel(private val getRateHistory: GetRateHistory) : ViewModel() {

    var rateHistoryLiveData: MutableLiveData<RateHistory> = MutableLiveData()
    var failureLiveData: MutableLiveData<Failure> = MutableLiveData()

    fun loadRateHistory(startDate: String, endDate: String) {
        getRateHistory(startDate, endDate) {
            when (it) {
                is Success<*> -> handleSuccess(it.result as RateHistory)
                is Failure -> handleFailure(it)
            }
        }
    }

    private fun handleSuccess(result: RateHistory) {
        rateHistoryLiveData.value = result
    }

    private fun handleFailure(failure: Failure) {
        failureLiveData.value = failure
    }

    class Factory
    @Inject constructor(val useCase: GetRateHistory) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RateHistoryViewModel(useCase) as T
        }
    }
}