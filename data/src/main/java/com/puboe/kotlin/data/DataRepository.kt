package com.puboe.kotlin.data

import com.puboe.kotlin.domain.RateHistory

class DataRepository(private val rateDataSource: RateDataSource) {

    fun getRateHistory(startAt: String, endAt: String): RateHistory {
        return rateDataSource.getRateHistory(startAt, endAt)
    }

}

interface RateDataSource {
    fun getRateHistory(startAt: String, endAt: String): RateHistory
}