package com.orlinskas.cocktail.ui.viewstate

import android.os.Bundle

interface ViewState {

    fun onRestoreState(bundle: Bundle?) {}

    fun onSaveState(bundle: Bundle?) {}
}
