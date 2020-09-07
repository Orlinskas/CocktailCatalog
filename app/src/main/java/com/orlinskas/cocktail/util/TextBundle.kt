package com.orlinskas.cocktail.util

import androidx.annotation.Keep
import androidx.annotation.StringRes

@Keep
data class TextBundle(@StringRes val textRes: Int, val varargs: Any? = null)
