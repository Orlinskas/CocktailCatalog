package com.orlinskas.cocktail.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.exception.HandledException

object Dialogs {

    fun exceptionDialog(context: Context, exception: HandledException) =
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.dialog_error_title))
            setMessage(exception.getText(context))
            setCancelable(true)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()

    fun exceptionDialog(context: Context, exception: HandledException, retry: () -> Unit) =
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.dialog_error_title))
            setMessage(exception.getText(context))
            setCancelable(true)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(R.string.retry) { _, _ ->  retry.invoke()}
        }.create()

    fun simpleDialog(
        context: Context,
        titleRes: Int,
        messageRes: Int? = null,
        message: String? = null,
        positiveTextRes: Int? = null,
        negativeTextRes: Int? = null,
        positiveCallback: (() -> (Unit))? = null,
        negativeCallback: (() -> (Unit))? = null,
        cancellable: Boolean? = null
    ) =
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(titleRes))
            messageRes?.let {
                setMessage(context.getString(messageRes))
            }
            message?.let {
                setMessage(it)
            }
            if (positiveTextRes == null) {
                setPositiveButton(android.R.string.ok) { _, _ ->
                    positiveCallback?.invoke()
                }
            } else {
                setPositiveButton(positiveTextRes) { _, _ ->
                    positiveCallback?.invoke()
                }
            }
            negativeTextRes?.let {
                setNegativeButton(R.string.retry) { _, _ ->  negativeCallback?.invoke()}
            }
            cancellable?.let {
                setCancelable(it)
            }
        }.create()
}
