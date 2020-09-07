package com.orlinskas.cocktail.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.orlinskas.cocktail.util.TextBundle
import kotlin.reflect.KClass

fun Context?.toast(message: String) {
    this?.let {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.toast(@StringRes messageRes: Int) {
    this?.let {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}

fun <T : Any> Context?.launchActivity(clazz: KClass<T>, flags: Int? = null, callback: (Intent.() -> Unit)? = null) {
    this?.let {
        val intent = Intent(this, clazz.java)
        flags?.let {
            intent.flags += it
        }
        callback?.invoke(intent)
        startActivity(intent)
    }
}

fun <T : Any> Context?.launchActivity(clazz: KClass<T>, callback: (Intent.() -> Unit)? = null) = launchActivity(clazz, null, callback)

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

fun Context.getString(textBundle: TextBundle): String = getString(textBundle.textRes, textBundle.varargs)
