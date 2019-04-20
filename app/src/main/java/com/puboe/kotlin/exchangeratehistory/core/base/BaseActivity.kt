package com.puboe.kotlin.exchangeratehistory.core.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.puboe.kotlin.domain.ClientFailure
import com.puboe.kotlin.domain.Failure
import com.puboe.kotlin.domain.NetworkFailure
import com.puboe.kotlin.domain.ServerFailure
import com.puboe.kotlin.exchangeratehistory.MainApplication
import com.puboe.kotlin.exchangeratehistory.R
import com.puboe.kotlin.exchangeratehistory.core.di.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    internal val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as MainApplication).appComponent
    }

    fun handleError(failure: Failure) {
        when (failure) {
            is NetworkFailure -> showError(getString(R.string.network_error_message))
            is ServerFailure -> showError(getString(R.string.server_error_message))
            is ClientFailure -> showError(getString(R.string.client_error_message))
        }
    }

    private fun showError(message: String) {
        val snackbar = Snackbar.make(findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(getColor(R.color.error))
        snackbar.show()
    }
}