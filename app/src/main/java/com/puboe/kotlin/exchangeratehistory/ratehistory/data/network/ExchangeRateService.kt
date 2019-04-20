package com.puboe.kotlin.exchangeratehistory.ratehistory.data.network

import com.puboe.kotlin.exchangeratehistory.ratehistory.data.RateHistory
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