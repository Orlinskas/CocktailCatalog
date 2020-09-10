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
}
