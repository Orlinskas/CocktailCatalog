package com.orlinskas.cocktail.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(
    val error: String?,
    val message: String?
)

data class CategoriesResponse(
    val drinks: List<Drink>
)

data class Drink(
    val strCategory: String
)

data class DrinksResponse(
    val drinks: List<DrinkResponse>
)

@Keep
data class DrinkResponse(
    @SerializedName("idDrink")
    val id: String,
    @SerializedName("strDrink")
    val name: String,
    @SerializedName("strCategory")
    val category: String,
    @SerializedName("strAlcoholic")
    val alcoholic: String,
    @SerializedName("strInstructions")
    val description: String,
    @SerializedName("strGlass")
    val glass: String,
    @SerializedName("strDrinkThumb")
    val imageUrl: String,
    @SerializedName("strIngredient1")
    val ingredient1: String,
    @SerializedName("strIngredient2")
    val ingredient2: String,
    @SerializedName("strIngredient3")
    val ingredient3: String,
    @SerializedName("strIngredient4")
    val ingredient4: String,
    @SerializedName("strIngredient5")
    val ingredient5: String,
    @SerializedName("strIngredient6")
    val ingredient6: String,
    @SerializedName("strIngredient7")
    val ingredient7: String
)
