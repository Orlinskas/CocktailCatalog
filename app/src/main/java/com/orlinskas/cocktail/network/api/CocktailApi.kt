package com.orlinskas.cocktail.network.api

import com.orlinskas.cocktail.network.CategoriesResponse
import com.orlinskas.cocktail.network.DrinksResponse
import com.orlinskas.cocktail.network.Urls
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET(Urls.GET_COCKTAIL_BY_NAME)
    fun getCocktails(name: String): Call<DrinksResponse>

    @GET(Urls.GET_CATEGORIES_LIST)
    fun getCategories(): Call<CategoriesResponse>

    @GET(Urls.GET_COCKTAILS_BY_FILTER)
    fun getCategoriesDrinks(@Query("c") category: String): Call<DrinksResponse>
}
