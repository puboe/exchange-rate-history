package com.puboe.kotlin.domain

import com.google.gson.annotations.SerializedName

data class RateHistory(
    val base: String,
    @SerializedName("start_at") val startAt: String,
    @SerializedName("end_at") val endAt: String,
    val rates: Map<String, Map<String, Double>>
)
