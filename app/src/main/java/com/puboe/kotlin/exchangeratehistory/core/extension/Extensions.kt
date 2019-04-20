package com.puboe.kotlin.exchangeratehistory.core.extension

import com.jjoe64.graphview.series.DataPoint
import com.puboe.kotlin.domain.RateHistory
import com.puboe.kotlin.domain.RateHistory.Companion.datePattern
import com.puboe.kotlin.domain.RateHistory.Companion.symbol
import java.text.SimpleDateFormat

fun RateHistory.adaptToGraph(): Array<DataPoint> {
    val dateFormat = SimpleDateFormat(datePattern)
    val data = ArrayList<DataPoint>(rates.size)

    rates.forEach { key, value ->
        data.add(DataPoint(dateFormat.parse(key), value[symbol] as Double))
    }
    // Sort data by date.
    data.sortBy { it.x }

    return data.toArray(emptyArray())
}