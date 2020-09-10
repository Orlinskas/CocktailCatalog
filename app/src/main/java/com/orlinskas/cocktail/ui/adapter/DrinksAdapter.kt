package com.orlinskas.cocktail.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.data.model.Categories
import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.databinding.ItemCocktailBinding
import com.orlinskas.cocktail.extensions.bindWith

class DrinksAdapter : RecyclerView.Adapter<DrinksAdapter.Holder>() {

    private val _data = mutableListOf<Cocktail>()
    private val _pagedInfoMap = mutableMapOf<Int, Categories>()

    val pagedInfoMap: Map<Int, Categories>
        get() = _pagedInfoMap

    var onCocktailClick: ((Cocktail) -> (Unit))? = null
    var onNextItemPosition: ((Int) -> (Unit))? = null

    fun addData(data: List<Cocktail>, categories: Categories) {
        val position = _data.lastIndex
        _data.addAll(data)
        _pagedInfoMap[position] = categories
        notifyItemRangeChanged(position, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent.bindWith(R.layout.item_cocktail))

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cocktail = _data[position]

        with(holder.binding) {
            this.cocktail = cocktail

            root.setOnClickListener {
                onCocktailClick?.invoke(cocktail)
            }
        }

        onNextItemPosition?.invoke(position)
    }

    class Holder(val binding: ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root)
}