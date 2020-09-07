package com.orlinskas.cocktail.extensions

import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField

fun Bundle?.putString(key: String, field: ObservableField<String?>) =
    this?.putString(key, field.get())

fun <T : Parcelable> Bundle?.putArrayList(key: String, arrayList: ObservableArrayList<T>) =
    this?.putParcelableArrayList(key, arrayList)

fun ObservableField<String?>.fromBundle(bundle: Bundle?, key: String) {
    if (bundle == null) {
        set(null)
    } else {
        set(bundle.getString(key))
    }
}

fun <T : Parcelable> ObservableArrayList<T>.fromBundle(bundle: Bundle?, key: String) {
    if (bundle == null) {
        addAll(emptyList())
    } else {
        addAll(bundle.getParcelableArrayList<T>(key) as Collection<T>)
    }
}
