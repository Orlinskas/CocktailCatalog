package com.orlinskas.cocktail.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.FragmentDrinkCategoriesBinding
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.ui.adapter.DrinkCategoriesAdapter
import com.orlinskas.cocktail.viewmodel.MainViewModel
import javax.inject.Inject

class DrinkCategoriesFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: MainViewModel

    private val binding: FragmentDrinkCategoriesBinding by fragmentBinding()
    private lateinit var adapter: DrinkCategoriesAdapter

    override val layoutResId = R.layout.fragment_drink_categories

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.state = viewModel.state

        adapter = DrinkCategoriesAdapter()

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        showProgressDialog()

        viewModel.drinkCategoriesLiveData.singleObserve(viewLifecycleOwner) {
            adapter.setData(it)
            hideProgressDialog()
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.drinkCategoriesLiveData.postValue(adapter.getData())
    }
}