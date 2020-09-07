package com.orlinskas.cocktail.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orlinskas.cocktail.data.dao.CocktailDao
import com.orlinskas.cocktail.data.model.Cocktail

@Database(
    entities = [
        Cocktail::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase(), ErasableData {

    abstract fun cocktailDao(): CocktailDao

    override fun erase() {
        clearAllTables()
    }
}
