package com.orlinskas.cocktail.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val throwable = t ?: Exception(message)

        if (priority != Log.ERROR || priority != Log.ASSERT) {
            return
        }

        FirebaseCrashlytics.getInstance().setCustomKey("priority", priority)
        FirebaseCrashlytics.getInstance().setCustomKey("tag", tag ?: "EmptyTag")
        FirebaseCrashlytics.getInstance().setCustomKey("message", message)
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}
