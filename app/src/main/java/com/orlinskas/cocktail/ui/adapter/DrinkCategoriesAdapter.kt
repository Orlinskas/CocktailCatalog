package com.orlinskas.cocktail.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.ItemCategoriesBinding
import com.orlinskas.cocktail.extensions.bindWith

class DrinkCategoriesAdapter : RecyclerView.Adapter<DrinkCategoriesAdapter.Holder>() {

    private val _data = mutableListOf<String>()

    var onCategoriesClick: ((String) -> (Unit))? = null

    fun setData(data: List<String>) {
        _data.clear()
        _data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent.bindWith(R.layout.item_categories))

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val categories = _data[position]

        with(holder.binding) {
            name.text = categories

            root.setOnClickListener {
                onCategoriesClick?.invoke(categories)
            }
        }
    }

    class Holder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)
}