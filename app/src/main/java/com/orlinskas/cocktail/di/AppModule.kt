package com.orlinskas.cocktail.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideApplicationContext(): Context = context
}
