package com.puboe.kotlin.exchangeratehistory

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.puboe.kotlin.domain.Failure
import com.puboe.kotlin.domain.RateHistory
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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

        rateHistoryViewModel.rateHistoryLiveData.observeForever(rateObserver)
        rateHistoryViewModel.failureLiveData.observeForever(failureObserver)

        button.setOnClickListener {
            showLoading()
            rateHistoryViewModel.loadRateHistory(extractDate(start_date), extractDate(end_date))
        }
    }

    /**
     * Plot the given data into the graph.
     */
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

    private fun setupView() {
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

    /**
     * Extract date from [DatePicker] instance.
     */
    private fun extractDate(datePicker: DatePicker): String {
        return datePicker.year.toString() + "-" + datePicker.month.toString() + "-" + datePicker.dayOfMonth.toString()
    }
}
