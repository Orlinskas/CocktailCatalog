package com.orlinskas.cocktail.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orlinskas.cocktail.R

open class ProgressDialogFragment : FullScreenDialog() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        View.inflate(context, R.layout.dialog_progress, container)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }
}
