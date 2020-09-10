package com.orlinskas.cocktail.network.client

import com.orlinskas.cocktail.extensions.call
import com.orlinskas.cocktail.network.api.CocktailApi
import javax.inject.Inject

class CocktailApiClient @Inject constructor(private val api: CocktailApi) {

    fun getCategories() = api.getCategories().call()

    fun getCategoriesDrinks(category: String) = api.getCategoriesDrinks(category).call()
}
