package com.orlinskas.cocktail.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.extensions.fromJson

class TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun cocktailsToJson(cocktails: List<Cocktail>): String = gson.toJson(cocktails)

    @TypeConverter
    fun jsonToCocktails(json: String): List<Cocktail> = gson.fromJson(json)

    @TypeConverter
    fun cocktailToJson(cocktail: Cocktail): String = gson.toJson(cocktail)

    @TypeConverter
    fun jsonToCocktail(json: String): Cocktail = gson.fromJson(json)

    @TypeConverter
    fun ingredientsToJson(ingredients: List<String>): String = gson.toJson(ingredients)

    @TypeConverter
    fun jsonToIngredients(json: String): List<String> = gson.fromJson(json)

}
