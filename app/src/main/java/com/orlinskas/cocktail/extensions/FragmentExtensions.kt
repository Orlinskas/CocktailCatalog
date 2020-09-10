package com.orlinskas.cocktail.extensions

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.withArgs(func: Bundle.() -> Unit): T {
    val args = Bundle()
    func.invoke(args)
    arguments = args
    return this
}

fun Fragment.toast(message: String) {
    context?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}
