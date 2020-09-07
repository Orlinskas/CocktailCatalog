package com.orlinskas.cocktail.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.viewmodel.SplashViewModel

private const val SPLASH_DELAY_TIME = 800L
private const val MESSAGE_ID = 1

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel

    private val handler = Handler(Handler.Callback {
        if (it.arg1 == 1) {
            MainActivity.startFromSplash(this)
        } else {
            // send to other activity
        }
        true
    })

    override fun onScreenOpened() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = getViewModel()

        viewModel.isDoneLiveData.singleObserve(this) {
            val message = Message()
            message.what = MESSAGE_ID
            message.arg1 = if (it) 1 else 0
            handler.sendMessageDelayed(message, SPLASH_DELAY_TIME)
        }
    }
}
