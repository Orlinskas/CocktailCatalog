package com.orlinskas.cocktail.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.orlinskas.cocktail.data.Tables
import com.orlinskas.cocktail.data.model.Cocktail

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktail: Cocktail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktails(cocktails: List<Cocktail>): List<Long>

    @Query("SELECT * FROM ${Tables.COCKTAILS}")
    fun getCocktailsDirectly(): List<Cocktail>

    @Query("SELECT * FROM ${Tables.COCKTAILS}")
    fun getCocktails(): LiveData<List<Cocktail>>

    @Query("SELECT * FROM ${Tables.COCKTAILS} WHERE id=:id")
    fun getCocktailsByIdDirectly(id: Long): Cocktail

    @Query("SELECT * FROM ${Tables.COCKTAILS} WHERE id=:id")
    fun getCocktailById(id: Long): LiveData<Cocktail>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCocktails(list: List<Cocktail>)

    @Query("DELETE FROM ${Tables.COCKTAILS}")
    fun clearCocktails()

    @Query("DELETE FROM ${Tables.COCKTAILS} WHERE id=:id")
    fun removeCocktail(id: Long): Int

}