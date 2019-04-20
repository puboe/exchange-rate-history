package com.puboe.kotlin.exchangeratehistory.ratehistory

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.puboe.kotlin.exchangeratehistory.R
import com.puboe.kotlin.exchangeratehistory.core.Failure
import com.puboe.kotlin.exchangeratehistory.core.base.BaseActivity
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.RateHistory
import com.puboe.kotlin.exchangeratehistory.ratehistory.extension.adaptToGraph
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RateHistoryActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val rateHistoryViewModel: RateHistoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RateHistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        setupView()

        val rateObserver = Observer<RateHistory> { history ->
            hideLoading()
            plotData(history)
        }

        val failureObserver = Observer<Failure> { error ->
            hideLoading()
            handleError(error)
        }

        rateHistoryViewModel.rateHistoryLiveData.observe(this, rateObserver)
        rateHistoryViewModel.failureLiveData.observe(this, failureObserver)

        button.setOnClickListener {
            showLoading()
            rateHistoryViewModel.loadRateHistory(extractDate(start_date), extractDate(end_date))
        }
    }

    /**
     * Plot the given data into the graph.
     */
    private fun plotData(history: RateHistory) {
        val data = history.adaptToGraph()
        val series = LineGraphSeries<DataPoint>(data)

        graph.viewport.setMinX(data.first().x)
        graph.viewport.setMaxX(data.last().x)
        graph.viewport.setXAxisBoundsManual(true)
        graph.viewport.setScalable(true)
        graph.gridLabelRenderer.isHorizontalLabelsVisible = true

        graph.addSeries(series)
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun setupView() {
        setupDatePickers()
        setupChart()
    }

    private fun setupDatePickers() {
        start_date.maxDate = Calendar.getInstance().timeInMillis
        end_date.maxDate = start_date.maxDate

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -14)
        start_date.updateDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        end_date.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(year, monthOfYear, dayOfMonth)
            start_date.maxDate = calendar.timeInMillis
        }
    }

    private fun setupChart() {
        graph.gridLabelRenderer.labelFormatter = (DateAsXAxisLabelFormatter(this, SimpleDateFormat("dd/MM/yy")))
        graph.gridLabelRenderer.isHorizontalLabelsVisible = false
        graph.gridLabelRenderer.setHorizontalLabelsAngle(45)
    }

    /**
     * Extract date from [DatePicker] instance.
     */
    private fun extractDate(datePicker: DatePicker): String {
        return datePicker.year.toString() + "-" + datePicker.month.toString() + "-" + datePicker.dayOfMonth.toString()
    }
}
