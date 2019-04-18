package com.puboe.kotlin.domain

import com.puboe.kotlin.domain.Callback

interface DataRepository {

    fun getRateHistory(startDate: String, endDate: String): Callback
}