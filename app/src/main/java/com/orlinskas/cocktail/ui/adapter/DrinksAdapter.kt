package com.orlinskas.cocktail.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.data.model.Cocktail
import com.orlinskas.cocktail.databinding.ItemCocktailBinding
import com.orlinskas.cocktail.extensions.bindWith

class DrinksAdapter : RecyclerView.Adapter<DrinksAdapter.Holder>() {

    private val _data = mutableListOf<Cocktail>()

    var onCocktailClick: ((Cocktail) -> (Unit))? = null

    fun setData(data: List<Cocktail>) {
        _data.clear()
        _data.addAll(data)
        notifyDataSetChanged()
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
    }

    class Holder(val binding: ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root)
}