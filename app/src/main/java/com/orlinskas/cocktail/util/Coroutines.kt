package com.orlinskas.cocktail.util

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import timber.log.Timber

val IO = object : CoroutineScope {
    override val coroutineContext = Dispatchers.IO
}

val MAIN = object : CoroutineScope {
    override val coroutineContext = Dispatchers.Main
}

@Suppress("DeferredResultUnused")
fun io(block: suspend CoroutineScope.() -> (Unit)) {
    val catchingBlock: suspend CoroutineScope.() -> (Unit) = {
        try {
            block.invoke(this)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Handler(Looper.getMainLooper())
                .post { throw throwable }
        }
    }

    IO.async(Dispatchers.IO, block = catchingBlock)
}
