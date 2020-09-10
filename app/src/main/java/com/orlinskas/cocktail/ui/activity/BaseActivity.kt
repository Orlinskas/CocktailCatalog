package com.orlinskas.cocktail.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.orlinskas.cocktail.exception.HandledException
import com.orlinskas.cocktail.ui.dialog.ProgressDialogFragment
import com.orlinskas.cocktail.di.Injector
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.ui.dialog.Dialogs
import com.orlinskas.cocktail.viewmodel.BaseViewModel

abstract class BaseActivity : AppCompatActivity() {

    val viewModelList = mutableSetOf<BaseViewModel>()

    private var isProgressDialogShow = false
    private var progressDialog: ProgressDialogFragment? = null

    abstract fun onScreenOpened()

    fun <T : BaseActivity> sendScreenToAnalytics(activity: T) {

    }

    override fun onResume() {
        if (isProgressDialogShow) {
            showProgressDialog()
        }

        isProgressDialogShow = false

        super.onResume()
    }

    override fun onPause() {

        if (isProgressDialogShow) {
            hideProgressDialog()
        }

        super.onPause()
    }

    fun <T : ViewDataBinding> bindContentView(@LayoutRes layoutRes: Int): T =
        DataBindingUtil.setContentView(this, layoutRes)

    /**
     * Removes progress dialog if it was shown and shows default dialog with error message from [HandledException.getText]
     */
    protected open fun handleException(exception: HandledException) {
        hideProgressDialog()
        Dialogs.exceptionDialog(this, exception).show()
    }

    /**
     * Shows fullscreen not cancelable dialog with spinner
     */
    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment()
        }

        if (!progressDialog!!.isAdded || !progressDialog!!.isVisible) {
            progressDialog!!.show(supportFragmentManager, "progress-dialog")
        }
    }

    /**
     * Hides progress dialog
     */
    fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    fun <T : BaseViewModel> viewModelOf(modelClass: Class<T>): T {
        val factory = Injector.viewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(modelClass)
        viewModel.exceptionLiveData.singleObserve(this) { handleException(it) }
        viewModel.attachLifecycle(this)
        viewModelList.removeAll { it::class.java.name == viewModel::class.java.name }
        viewModelList.add(viewModel)
        return viewModel
    }

    inline fun <reified T : BaseViewModel> getViewModel(): T {
        return viewModelOf(T::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModelList.forEach { it.onSaveState(outState) }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModelList.forEach { it.onRestoreState(savedInstanceState) }
    }
}
