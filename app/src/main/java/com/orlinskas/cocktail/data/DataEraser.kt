package com.orlinskas.cocktail.data

import android.util.Log
import timber.log.Timber

class DataEraser(vararg erasableData: ErasableData) : ErasableData {

    private val data = erasableData

    override fun erase() {
        data.forEach {
            it.erase()
        }
        Timber.log(Log.ASSERT, "Data erased")
    }
}
