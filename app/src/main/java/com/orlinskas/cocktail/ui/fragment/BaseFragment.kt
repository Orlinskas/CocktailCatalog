package com.orlinskas.cocktail.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.orlinskas.cocktail.di.Injector
import com.orlinskas.cocktail.exception.HandledException
import com.orlinskas.cocktail.ui.dialog.Dialogs
import com.orlinskas.cocktail.ui.dialog.ProgressDialogFragment
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.viewmodel.BaseViewModel

abstract class BaseFragment : Fragment() {

    private var progressDialog: ProgressDialogFragment? = null
    protected val viewModelList = mutableSetOf<BaseViewModel>()
    protected var menu: Menu? = null

    protected open var optionsMenuRes: Int = 0
    protected open lateinit var fragmentDataBinding: ViewDataBinding

    abstract val layoutResId: Int

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

    protected inline fun <reified T : ViewDataBinding> fragmentBinding(): BindingLazy<T> = BindingLazy()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(layoutResId != 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResId, container, false)
        return fragmentDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelList.forEach { it.onRestoreState(savedInstanceState) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModelList.forEach { it.onSaveState(outState) }
    }

    /**
     * Removes progress dialog if it was shown and shows default dialog with error message from [HandledException.getText]
     */
    open fun handleException(exception: HandledException) {
        hideProgressDialog()
        Dialogs.exceptionDialog(requireContext(), exception)
    }

    /**
     * Shows fullscreen not cancelable dialog with spinner
     */
    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment()
        }

        if (!progressDialog!!.isAdded || !progressDialog!!.isVisible) {
            progressDialog!!.show(childFragmentManager, "progress-dialog")
        }
    }

    /**
     * Hides progress dialog
     */
    fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (optionsMenuRes != 0) {
            inflater.inflate(optionsMenuRes, menu)
            this.menu = menu
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected inner class BindingLazy<out T : ViewDataBinding> : Lazy<T> {

        override val value: T
            get() = fragmentDataBinding as T

        override fun isInitialized() = ::fragmentDataBinding.isInitialized
    }
}
