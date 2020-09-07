package com.orlinskas.cocktail.di

import com.orlinskas.cocktail.data.DataModule
import com.orlinskas.cocktail.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        AppModule::class,
        DataModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun viewModelFactory(): AppViewModelFactory

}
