package com.orlinskas.cocktail.network

object Urls {

    const val BASE_URL = "https://www.thecocktaildb.com/"
    private const val API_KEY = "1"
    private const val API_VERSION = "api/json/v1/$API_KEY"

    const val GET_COCKTAIL_BY_NAME = "$API_VERSION/search.php?s={name}"
    const val GET_COCKTAILS_BY_LETTER = "$API_VERSION/search.php?f={letter}"

    const val GET_INGREDIENT_BY_NAME = "$API_VERSION/search.php?i={name}"

    const val GET_COCKTAILS_BY_INGREDIENT = "$API_VERSION/filter.php?i={ingredient}"
    const val GET_COCKTAILS_BY_FILTER = "$API_VERSION/filter.php"

    const val GET_COCKTAIL_DETAILS_BY_ID = "$API_VERSION/lookup.php?i={id}"
    const val GET_INGREDIENT_DETAILS_BY_ID = "$API_VERSION/lookup.php?iid={id}"

    const val GET_RANDOM_COCKTAIL = "$API_VERSION/random.php"

    const val GET_CATEGORIES_LIST = "$API_VERSION/list.php?c=list"

    fun getList(param: Param) =
        "$API_VERSION/list.php?${param.url}=list"

    fun filterByValue(param: Param) =
        "$API_VERSION/filter.php?${param.url}={value}"

    fun getIngredientImage(name: String, ingredientImageQuality: IngredientImageQuality) =
        "images/ingredients/{ingredient}-${ingredientImageQuality.url}.png"

    enum class Param(val url: String) {
        CATEGORIES("c"),
        GLASSES("g"),
        INGREDIENTS("i"),
        ALCOHOLIC("a")
    }

    enum class IngredientImageQuality(val url: String) {
        LARGE(""),
        MEDIUM("Medium"),
        SMALL("Small")
    }
}
