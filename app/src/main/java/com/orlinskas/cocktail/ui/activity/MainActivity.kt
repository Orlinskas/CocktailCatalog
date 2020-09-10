package com.orlinskas.cocktail.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.ActivityMainBinding
import com.orlinskas.cocktail.extensions.launchActivity
import com.orlinskas.cocktail.extensions.singleObserve
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
        hostFragment.navController.navigate(R.id.drinkFragment)

        viewModel.getCocktailsCategoriesRemote().singleObserve(this) {
            viewModel.drinkCategoriesLiveData.postValue(it)
        }

        binding.filterImage.setOnClickListener {
            hostFragment.navController.navigate(R.id.drinkCategoriesFragment)
        }

        viewModel.onCategoriesChange = {
            binding.title.text = it.name
        }
    }

    override fun onBackPressed() {
        if (hostFragment.navController.currentDestination?.id == R.id.drinkFragment) {
            finish()
        }
        super.onBackPressed()
    }

    companion object {

        fun startFromSplash(context: Context) =
            context.launchActivity(MainActivity::class)
    }
}
