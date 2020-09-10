package com.orlinskas.cocktail

import android.app.Application
import com.orlinskas.cocktail.di.AppModule
import com.orlinskas.cocktail.di.DaggerAppComponent
import com.orlinskas.cocktail.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        Injector.init(appComponent)

        AppInitializer.onApplicationCreated(this)
    }
}
