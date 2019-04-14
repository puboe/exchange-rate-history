package com.puboe.kotlin.domain

data class RateHistory(val base: String, val endAt: String, val startAt: String, val rates: List<Rate>) {
}