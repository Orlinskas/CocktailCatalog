package com.orlinskas.cocktail.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Is used to monitor network connectivity.
 */
@Singleton
@SuppressLint("Deprecation")
class ConnectivityMonitor @Inject constructor(context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Checks if any network is connected right now.
     */
    @Suppress("DEPRECATION")
    fun isNetworkConnectionAvailable() = connectivityManager.activeNetworkInfo?.isConnected ?: false
}
