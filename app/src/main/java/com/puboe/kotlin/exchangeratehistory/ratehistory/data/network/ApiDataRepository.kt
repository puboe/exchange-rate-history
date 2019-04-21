package com.puboe.kotlin.exchangeratehistory.ratehistory.data.network

import com.puboe.kotlin.exchangeratehistory.core.*
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiDataRepository : DataRepository {

    companion object {
        private const val BASE_URL = "https://api.exchangeratesapi.io/"
        private const val SYMBOL = "USD"
    }

    private val service: ExchangeRateService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ExchangeRateService::class.java)
    }

    override fun getRateHistory(startDate: String, endDate: String): Callback {
        return try {
            val call = service.getRateHistory(SYMBOL, startDate, endDate)
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Success(response.body())
                false -> {
                    if (response.code() >= 500) {
                        ServerFailure()
                    } else {
                        ClientFailure()
                    }
                }
            }
        } catch (exception: Throwable) {
            NetworkFailure()
        }
    }
}