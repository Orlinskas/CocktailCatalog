package com.orlinskas.cocktail.livedata

import androidx.lifecycle.MutableLiveData
import com.orlinskas.cocktail.data.model.Categories
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinkCategoriesLiveData @Inject constructor() : MutableLiveData<List<Categories>>()

