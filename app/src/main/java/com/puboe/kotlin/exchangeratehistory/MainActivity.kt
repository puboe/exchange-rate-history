package com.puboe.kotlin.exchangeratehistory

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.puboe.kotlin.domain.ApiDataRepository
import com.puboe.kotlin.domain.Failure
import com.puboe.kotlin.domain.RateHistory
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var rateHistoryViewModel: RateHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureView()

        val useCase = GetRateHistory(ApiDataRepository())

        rateHistoryViewModel =
            ViewModelProviders.of(this, RateHistoryViewModel.Factory(useCase)).get(RateHistoryViewModel::class.java)

        // Create the observer which updates the UI.
        val rateObserver = Observer<RateHistory> { history ->
            Log.e("TAG", history.toString())
            hideLoading()
            plotData(history)
        }

        val failureObserver = Observer<Failure> {
            Log.e("TAG", "${it.message}")
            hideLoading()
            Snackbar.make(graph, it.message, Snackbar.LENGTH_LONG).show()
        }

        rateHistoryViewModel.rateHistoryLiveData.observeForever(rateObserver)
        rateHistoryViewModel.failureLiveData.observeForever(failureObserver)

        button.setOnClickListener {
            Log.e("TAG", extractDate(start_date))
            Log.e("TAG", extractDate(end_date))
            showLoading()
            rateHistoryViewModel.loadRateHistory(extractDate(start_date), extractDate(end_date))
        }
    }

    private fun plotData(history: RateHistory) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val data = ArrayList<DataPoint>(history.rates.size)

        history.rates.forEach { key, value ->
            data.add(DataPoint(formatter.parse(key), value["USD"] as Double))
        }
        // Sort data by date.
        data.sortBy { it.x }

        val series = LineGraphSeries<DataPoint>(data.toArray(emptyArray()))
        graph.gridLabelRenderer.labelFormatter = (DateAsXAxisLabelFormatter(this))
        graph.viewport.setMinX(data.first().x)
        graph.viewport.setMaxX(data.last().x)
        graph.viewport.setXAxisBoundsManual(true)

        graph.addSeries(series)
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun configureView() {
        start_date.maxDate = Calendar.getInstance().timeInMillis
        end_date.maxDate = start_date.maxDate

        end_date.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            start_date.maxDate = calendar.timeInMillis
        }
    }

    private fun extractDate(datePicker: DatePicker): String {
        return datePicker.year.toString() + "-" + datePicker.month.toString() + "-" + datePicker.dayOfMonth.toString()
    }
}
