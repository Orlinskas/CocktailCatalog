package com.orlinskas.cocktail.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.FragmentDrinksBinding
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.ui.adapter.DrinksAdapter
import com.orlinskas.cocktail.viewmodel.MainViewModel
import javax.inject.Inject

class DrinksFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: MainViewModel

    private val binding: FragmentDrinksBinding by fragmentBinding()
    private lateinit var adapter: DrinksAdapter

    override val layoutResId = R.layout.fragment_drinks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.state = viewModel.state

        adapter = DrinksAdapter()

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        showProgressDialog()

        val args: DrinksFragmentArgs by navArgs()
        args.category?.let {
            viewModel.getCategoriesDrinks(it).singleObserve(viewLifecycleOwner) { drinks ->
                adapter.setData(drinks)
                hideProgressDialog()
            }
        }
    }
}