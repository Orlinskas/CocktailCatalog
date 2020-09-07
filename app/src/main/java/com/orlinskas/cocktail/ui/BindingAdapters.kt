package com.orlinskas.cocktail.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.orlinskas.cocktail.extensions.onTextChanged

@BindingAdapter("hideErrorsOnInputChange")
fun hideErrorsOnInputChange(view: TextInputLayout, @Suppress("UNUSED_PARAMETER") value: Boolean) {
    view.onTextChanged { view.error = null }
}
