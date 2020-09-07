package com.orlinskas.cocktail.network

import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.extensions.addIfNotNullTo

fun DrinkResponse.toModel() =
    Cocktail(
        id = id,
        name = name,
        category = category,
        alcoholic = alcoholic,
        description = description,
        glass = glass,
        imageUrl = imageUrl,
        ingredients = this.createIngredientsList()
    )

fun DrinkResponse.createIngredientsList(): MutableList<String> {

    return mutableListOf<String>().apply {
        ingredient1.addIfNotNullTo(this)
        ingredient2.addIfNotNullTo(this)
        ingredient3.addIfNotNullTo(this)
        ingredient4.addIfNotNullTo(this)
        ingredient5.addIfNotNullTo(this)
        ingredient6.addIfNotNullTo(this)
        ingredient7.addIfNotNullTo(this)
    }
}

fun DrinksResponse.toModel() = drinks.map { it.toModel() }


