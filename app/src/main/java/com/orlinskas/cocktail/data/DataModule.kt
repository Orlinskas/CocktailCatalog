package com.orlinskas.cocktail.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        val builder = Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Provides
    fun provideDataEraser(
        appDatabase: AppDatabase
    ): DataEraser =
        DataEraser(
            appDatabase
        )
}
