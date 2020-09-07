package com.orlinskas.cocktail.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.extensions.fromJson

class TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun cocktailToJson(cocktails: List<Cocktail>): String = gson.toJson(cocktails)

    @TypeConverter
    fun jsonToCocktail(json: String): List<Cocktail> = gson.fromJson(json)

}
