package com.puboe.kotlin.exchangeratehistory.ratehistory.data

import com.puboe.kotlin.exchangeratehistory.core.Callback

interface DataRepository {

    fun getRateHistory(startDate: String, endDate: String): com.puboe.kotlin.exchangeratehistory.core.Callback
}