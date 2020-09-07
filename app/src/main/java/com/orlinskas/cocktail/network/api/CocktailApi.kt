package com.orlinskas.cocktail.network.api

import com.orlinskas.cocktail.network.DrinksResponse
import com.orlinskas.cocktail.network.Urls
import retrofit2.Call
import retrofit2.http.GET

interface CocktailApi {

    @GET(Urls.GET_COCKTAIL_BY_NAME)
    fun getCocktails(name: String): Call<DrinksResponse>
}
