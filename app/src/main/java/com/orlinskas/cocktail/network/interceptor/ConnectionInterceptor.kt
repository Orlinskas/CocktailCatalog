package com.orlinskas.cocktail.network.interceptor

import com.orlinskas.cocktail.network.ConnectivityMonitor
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class ConnectionInterceptor @Inject constructor(private val connectivityMonitor: ConnectivityMonitor) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (connectivityMonitor.isNetworkConnectionAvailable()) {
            return chain.proceed(chain.request())
        } else {
            throw SocketTimeoutException()
        }
    }
}
