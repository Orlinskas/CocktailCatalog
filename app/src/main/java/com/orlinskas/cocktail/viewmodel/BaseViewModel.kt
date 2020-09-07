package com.orlinskas.cocktail.viewmodel

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orlinskas.cocktail.exception.HandledException
import com.orlinskas.cocktail.ui.viewstate.ViewState
import com.orlinskas.cocktail.util.Wish

open class BaseViewModel : ViewModel(), LifecycleOwner {

    private val viewStates = mutableSetOf<ViewState>()
    private val _exceptionLiveData = MutableLiveData<HandledException>()

    private lateinit var lifecycleOwner: LifecycleOwner

    val exceptionLiveData: LiveData<HandledException>
        get() = _exceptionLiveData

    fun attachLifecycle(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        onLifecycleAttached()
    }

    override fun getLifecycle() = lifecycleOwner.lifecycle

    protected fun postError(ex: HandledException) {
        _exceptionLiveData.postValue(ex)
    }

    protected fun <T> deliver(wish: Wish<T>, liveData: MutableLiveData<T>?) {
        if (wish.ok) {
            liveData?.postValue(wish.success)
        } else {
            postError(wish.failure)
        }
    }

    protected fun addSaveStateHandler(vararg states: ViewState) {
        viewStates.addAll(states)
    }

    fun onRestoreState(bundle: Bundle?) {
        viewStates.forEach {
            it.onRestoreState(bundle)
        }
    }

    fun onSaveState(bundle: Bundle?) {
        viewStates.forEach {
            it.onSaveState(bundle)
        }
    }

    /**
     * An callback that invokes when lifecycle owner is attached to current view model instance
     */
    open fun onLifecycleAttached() {}
}
