package com.orlinskas.cocktail.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Inflates and creates binding for a new view with [layoutId] view is attached to current [ViewGroup]
 */
fun <T : ViewDataBinding> ViewGroup.bindWith(@LayoutRes layoutId: Int): T {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return DataBindingUtil.inflate(inflater, layoutId, this, false)
}

fun CheckBox.nextChecked() {
    isChecked = !this.isChecked
}


