package com.puboe.kotlin.exchangeratehistory

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.puboe.kotlin.domain.ClientFailure
import com.puboe.kotlin.domain.Failure
import com.puboe.kotlin.domain.NetworkFailure
import com.puboe.kotlin.domain.ServerFailure
import com.puboe.kotlin.exchangeratehistory.di.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    internal val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as MainApplication).appComponent
    }

    fun handleError(failure: Failure) {
        when (failure) {
            is NetworkFailure -> showError(resources.getString(R.string.network_error_message))
            is ServerFailure -> showError(resources.getString(R.string.server_error_message))
            is ClientFailure -> showError(resources.getString(R.string.client_error_message))
        }
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}