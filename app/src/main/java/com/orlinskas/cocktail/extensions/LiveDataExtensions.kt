package com.orlinskas.cocktail.extensions

import androidx.annotation.Keep
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

/**
 * Removes all previously registered observers for current lifecycle owner and live data
 * Callback do not returns null values if it was passed to live data
 */
fun <T> LiveData<T>.singleObserve(lifecycleOwner: LifecycleOwner, observer: (T) -> (Unit)) {
    removeObservers(lifecycleOwner)
    observe(
        lifecycleOwner,
        Observer {
            if (it != null) {
                observer.invoke(it)
            }
        }
    )
}

@Keep
private data class LifecycleHolder<T>(var observer: Observer<T>? = null)

fun <X, Y> LiveData<X>.map(transform: (X) -> Y): LiveData<Y> = Transformations.map(this, transform)

fun <T> liveDataOf(block: (liveData: MutableLiveData<T>) -> (Unit)): LiveData<T> = MutableLiveData<T>().also {
    block.invoke(it)
}
