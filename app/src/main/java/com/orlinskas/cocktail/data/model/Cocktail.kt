package com.orlinskas.cocktail.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orlinskas.cocktail.data.Tables
import com.orlinskas.cocktail.network.Urls

@Keep
@Entity(tableName = Tables.COCKTAILS)
data class Cocktail(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String?,
    val alcoholic: String?,
    val description: String?,
    val glass: String?,
    val imageUrl: String?,
    val ingredients: List<String>?
)

fun Cocktail.imagePreviewUrl() = "$imageUrl/preview"

fun String.imageIngredientUrl(ingredientImageQuality: Urls.IngredientImageQuality) = "${Urls.BASE_URL}images/ingredients/$this-${ingredientImageQuality.url}.png"
