package com.orlinskas.cocktail.extensions

import android.app.Activity
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.textfield.TextInputLayout
import com.orlinskas.cocktail.R

fun EditText.cursorToEnd() {
    setSelection(this.text.length)
}

fun EditText.onTextChanged(callback: (String?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback.invoke(s?.toString())
        }
    })
}

fun EditText.onTextIntChanged(callback: (Int?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null) {
                try {
                    callback.invoke(s.toString().toInt())
                } catch (ex: NumberFormatException) {
                    callback.invoke(null)
                }
            } else {
                callback.invoke(null)
            }
        }
    })
}

fun TextInputLayout.onTextChanged(callback: (String?) -> Unit) = editText?.onTextChanged(callback)

fun EditText.onImeDoneAction(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback.invoke()
            true
        } else {
            false
        }
    }
}

fun TextInputLayout.onImeDoneAction(callback: () -> Unit) = editText?.onImeDoneAction(callback)

fun Spinner.onItemSelected(callback: (position: Int?) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            callback.invoke(null)
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback.invoke(position)
        }
    }
}

fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
    setTextColor(context.color(colorRes))
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun <T : RecyclerView.ViewHolder> T.getString(@StringRes res: Int): String = itemView.context.getString(
    res
)

fun View.nextVisibility() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

fun View.rotate() {
    this.rotation = this.rotation + 180f
}

fun ImageView.setImageUrlWithLoadCallback(url: String, loadCallBack: (Boolean) -> Unit) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadCallBack.invoke(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadCallBack.invoke(true)
                return false
            }
        })
        .into(this)
}

fun ImageView.setImageUrl(url: String, loadCallBack: (Boolean) -> Unit) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadCallBack.invoke(true)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadCallBack.invoke(true)
                return false
            }
        })
        .into(this)
}
