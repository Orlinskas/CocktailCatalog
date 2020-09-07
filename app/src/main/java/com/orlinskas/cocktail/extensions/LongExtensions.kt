package com.orlinskas.cocktail.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertToDateStr(format: String): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(this))
}

