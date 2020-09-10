package com.orlinskas.cocktail.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.orlinskas.cocktail.R

private const val SPLASH_DELAY_TIME = 800L
private const val MESSAGE_ID = 1

class SplashActivity : BaseActivity() {

    private val handler = Handler(Handler.Callback {
        if (it.arg1 == 1) {
            MainActivity.startFromSplash(this)
        }
        true
    })

    override fun onScreenOpened() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val message = Message()
        message.what = MESSAGE_ID
        message.arg1 = 1
        handler.sendMessageDelayed(message, SPLASH_DELAY_TIME)
    }
}
