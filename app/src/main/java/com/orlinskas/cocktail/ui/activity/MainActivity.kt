package com.orlinskas.cocktail.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.ActivityMainBinding
import com.orlinskas.cocktail.extensions.launchActivity
import com.orlinskas.cocktail.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var hostFragment: NavHostFragment

    override fun onScreenOpened() {
        sendScreenToAnalytics(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindContentView(R.layout.activity_main)
        viewModel = getViewModel()

        hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        hostFragment.navController.navigate(R.id.drinkCategoriesFragment)
    }

    override fun onBackPressed() {
        if (hostFragment.navController.currentDestination?.id == R.id.drinkCategoriesFragment) {
            finish()
        }
        super.onBackPressed()
    }

    companion object {

        fun start(context: Context) =
            context.launchActivity(MainActivity::class, Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        fun startFromSplash(context: Context) =
            context.launchActivity(MainActivity::class)
    }
}
