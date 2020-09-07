package com.orlinskas.cocktail.data.repository

import com.orlinskas.cocktail.data.AppDatabase
import com.orlinskas.cocktail.data.model.Cocktail
import javax.inject.Inject

class CocktailRepository @Inject constructor(appDatabase: AppDatabase) {

    private val cocktailDao = appDatabase.cocktailDao()

    fun getCocktails() = cocktailDao.getCocktails()

    fun getCocktailsBy(id: Long) = cocktailDao.getCocktailById(id)

    fun setCocktails(cocktails: List<Cocktail>) = cocktailDao.insertCocktails(cocktails)
}
