package com.orlinskas.cocktail.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.orlinskas.cocktail.extensions.launchActivity

class MainActivity : BaseActivity() {

    override fun onScreenOpened() {
        sendScreenToAnalytics(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {

        fun start(context: Context) =
            context.launchActivity(MainActivity::class, Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        fun startFromSplash(context: Context) =
            context.launchActivity(MainActivity::class)
    }
}
