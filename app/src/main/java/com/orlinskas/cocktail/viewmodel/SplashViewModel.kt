package com.orlinskas.cocktail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orlinskas.cocktail.network.ConnectivityMonitor
import javax.inject.Inject

class SplashViewModel : BaseViewModel() {

    @Inject lateinit var connectivityMonitor: ConnectivityMonitor

    val isDoneLiveData: LiveData<Boolean>
        get() {
            return MutableLiveData<Boolean>()
                .also {
                    it.postValue(connectivityMonitor.isNetworkConnectionAvailable())
                }
        }
}
