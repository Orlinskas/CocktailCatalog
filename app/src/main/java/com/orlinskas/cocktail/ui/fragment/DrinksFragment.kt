package com.orlinskas.cocktail.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.data.model.Categories
import com.orlinskas.cocktail.databinding.FragmentDrinksBinding
import com.orlinskas.cocktail.extensions.singleObserve
import com.orlinskas.cocktail.extensions.toast
import com.orlinskas.cocktail.ui.adapter.DrinksAdapter
import com.orlinskas.cocktail.viewmodel.MainViewModel
import javax.inject.Inject

class DrinksFragment : BaseFragment() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var adapter: DrinksAdapter

    override val layoutResId = R.layout.fragment_drinks

    private val binding: FragmentDrinksBinding by fragmentBinding()

    private var currentCategoryIndex = 0
    private var checkedCategories: List<Categories> = listOf()

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

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

        viewModel.drinkCategoriesLiveData.singleObserve(viewLifecycleOwner) { list ->
            checkedCategories = list.filter { it.isChecked }
            firstPage()
        }

        binding.recycleView.addOnScrollListener(object : PaginationScrollListener(binding.recycleView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                binding.isLoading = isLoading
                return isLoading
            }

            override fun loadMoreItems() {
                nextPage()
            }
        })

        adapter.onNextItemPosition = { position ->
            if (adapter.pagedInfoMap.containsKey(position)) {
                adapter.pagedInfoMap[position]?.let { it -> viewModel.onCategoriesChange?.invoke(it) }
            }
        }
    }

    private fun firstPage() {
        currentCategoryIndex = 0

        val categories = checkedCategories[currentCategoryIndex]
        viewModel.onCategoriesChange?.invoke(categories)

        viewModel.getCategoriesDrinks(categories.name).singleObserve(viewLifecycleOwner) { drinks ->
            adapter.addData(drinks, categories)
            hideProgressDialog()
        }
    }

    fun nextPage() {
        isLoading = true

        if (currentCategoryIndex < checkedCategories.size - 1) {
            currentCategoryIndex++
        } else {
            isLastPage = true
            toast("Last Page")
        }

        val categories = checkedCategories[currentCategoryIndex]

        viewModel.getCategoriesDrinks(categories.name).singleObserve(viewLifecycleOwner) { drinks ->
            adapter.addData(drinks, categories)
            isLoading = false
        }
    }

    abstract class PaginationScrollListener(private var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        abstract fun isLastPage(): Boolean

        abstract fun isLoading(): Boolean

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }
            }
        }

        abstract fun loadMoreItems()
    }

}