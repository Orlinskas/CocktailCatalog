package com.orlinskas.cocktail.viewmodel

import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.data.repository.CocktailRepository
import com.orlinskas.cocktail.extensions.liveDataOf
import com.orlinskas.cocktail.ui.viewstate.SearchViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : BaseViewModel() {

    val state = SearchViewState()

    init {
        addSaveStateHandler(state)
    }

    fun getCocktailsCategoriesRemote() = liveDataOf<List<String>> { liveData ->
        cocktailRepository.getRemoteCocktailsCategories { result ->
            result.onSuccess {
                liveData.postValue(it)
            }
            result.onFailure { ex ->
                postError(ex)
            }
        }
    }

    fun getCategoriesDrinks(category: String) = liveDataOf<List<Cocktail>> { liveData ->
        cocktailRepository.getRemoteCategoriesDrinks(category) { result ->
            result.onSuccess {
                liveData.postValue(it)
            }
            result.onFailure { ex ->
                postError(ex)
            }
        }
    }

}