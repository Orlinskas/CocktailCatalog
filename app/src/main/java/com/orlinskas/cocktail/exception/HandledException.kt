package com.orlinskas.cocktail.exception

import android.content.Context
import com.orlinskas.cocktail.R

open class HandledException constructor(val msg: String?, private val stringResource: Int?) : Exception() {

    constructor(message: String) : this(message, null)

    constructor(stringResource: Int) : this(null, stringResource)

    open fun getText(context: Context): String = msg ?: context.getString(stringResource!!)

    companion object {

        fun failedToLoadCocktails() = HandledException("Failed to load COCKTAILS")

    }
}

