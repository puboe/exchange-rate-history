package com.puboe.kotlin.exchangeratehistory.ratehistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.exchangeratehistory.core.Failure
import com.puboe.kotlin.exchangeratehistory.core.Success
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.RateHistory
import com.puboe.kotlin.exchangeratehistory.ratehistory.interactor.GetRateHistory
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