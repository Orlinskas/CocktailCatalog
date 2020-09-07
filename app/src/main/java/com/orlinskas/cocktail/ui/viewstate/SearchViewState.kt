package com.orlinskas.cocktail.ui.viewstate

import android.os.Bundle
import com.orlinskas.cocktail.data.SearchParameters

private const val KEY_PARAMETERS = "PARAMETERS"

class SearchViewState : ViewState {

    val searchParameters = SearchParameters()

    override fun onRestoreState(bundle: Bundle?) {
        super.onRestoreState(bundle)
        bundle?.getParcelable<SearchParameters>(KEY_PARAMETERS)?.let {
            searchParameters.apply(it)
        }
    }

    override fun onSaveState(bundle: Bundle?) {
        super.onSaveState(bundle)
        bundle?.putParcelable(KEY_PARAMETERS, searchParameters)
    }
}
