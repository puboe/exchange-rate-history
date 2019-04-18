package com.puboe.kotlin.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {

    @GET("history")
    fun getRateHistory(
        @Query("symbols") symbol: String,
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String
    ): Call<RateHistory>
}