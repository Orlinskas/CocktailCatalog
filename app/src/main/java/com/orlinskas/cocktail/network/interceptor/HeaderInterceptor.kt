package com.sandiplus.b2b.network.interceptor

import com.sandiplus.b2b.network.Urls
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")

        if (!Urls.urlRequiresAuth(chain.request().url().toString())) {
            builder.header("Cache-Control", "no-cache")
        }

        return chain.proceed(builder.build())
    }
}
