package com.orlinskas.cocktail.di

import android.app.Application
import com.orlinskas.cocktail.util.CrashlyticsTree
import okhttp3.OkHttpClient
import timber.log.Timber

object AppInitializer {

    fun initNetInterceptors(builder: OkHttpClient.Builder) = builder

    fun initCatalogDownloadInterceptors(builder: OkHttpClient.Builder) = builder

    fun onApplicationCreated(app: Application) {
//        Timber.plant(CrashlyticsTree())
    }
}