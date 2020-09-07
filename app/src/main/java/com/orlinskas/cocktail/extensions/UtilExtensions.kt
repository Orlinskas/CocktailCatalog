package com.orlinskas.cocktail.extensions

import android.os.Parcel
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal
import java.util.Locale

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Parcel.writeBooleanValue(value: Boolean) = writeByte(if (value) 1 else 0)

fun Parcel.readBooleanValue(): Boolean = readByte() == 1.toByte()

fun String?.addIfNotNullTo(list: MutableList<String>) {
    this?.let {
        list.add(it)
    }
}
