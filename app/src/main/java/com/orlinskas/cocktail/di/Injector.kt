package com.orlinskas.cocktail.di

object Injector : AppComponent {

    private lateinit var component: AppComponent

    fun init(component: AppComponent) {
        Injector.component = component
    }

    override fun viewModelFactory() = component.viewModelFactory()
}
