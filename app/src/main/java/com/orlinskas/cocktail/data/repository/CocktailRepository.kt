package com.orlinskas.cocktail.data.repository

import com.orlinskas.cocktail.data.AppDatabase
import com.orlinskas.cocktail.data.model.Categories
import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.livedata.DrinkCategoriesLiveData
import com.orlinskas.cocktail.network.client.CocktailApiClient
import com.orlinskas.cocktail.network.toModel
import com.orlinskas.cocktail.util.Wish
import com.orlinskas.cocktail.util.io
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    appDatabase: AppDatabase,
    private val client: CocktailApiClient
) {

    private val cocktailDao = appDatabase.cocktailDao()

    fun getRemoteCocktailsCategories(callback: ((Wish<List<Categories>>) -> (Unit))) {
        io {
            val result = client.getCategories()

            result.onSuccess { response ->
                val categories = response.drinks.map { Categories(it.strCategory, true) }
                callback.invoke(Wish(categories))
            }

            result.onFailure {
                callback.invoke(Wish(it))
            }
        }
    }

    fun getRemoteCategoriesDrinks(category: String, callback: ((Wish<List<Cocktail>>) -> (Unit))) {
        io {
            val result = client.getCategoriesDrinks(category)

            result.onSuccess { response ->
                callback.invoke(Wish(response.toModel()))
            }

            result.onFailure {
                callback.invoke(Wish(it))
            }
        }
    }
}
