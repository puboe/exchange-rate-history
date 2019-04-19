package com.puboe.kotlin.domain

interface DataRepository {

    fun getRateHistory(startDate: String, endDate: String): Callback
}