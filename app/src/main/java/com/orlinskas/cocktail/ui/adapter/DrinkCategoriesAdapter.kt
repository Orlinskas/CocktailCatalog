package com.orlinskas.cocktail.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.data.model.Categories
import com.orlinskas.cocktail.databinding.ItemCategoriesBinding
import com.orlinskas.cocktail.extensions.bindWith
import com.orlinskas.cocktail.extensions.nextChecked

class DrinkCategoriesAdapter : RecyclerView.Adapter<DrinkCategoriesAdapter.Holder>() {

    private val _data = mutableListOf<Categories>()

    var onCategoriesChanges: ((List<Categories>) -> (Unit))? = null

    fun setData(data: List<Categories>) {
        if (_data.containsAll(data) && data.containsAll(_data)) {
            notifyDataSetChanged()
        } else {
            _data.clear()
            _data.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun getData(): MutableList<Categories> {
        return _data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent.bindWith(R.layout.item_categories))

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val categories = _data[position]

        with(holder.binding) {
            name.text = categories.name
            checkBox.isChecked = categories.isChecked

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                categories.isChecked = isChecked
                onCategoriesChanges?.invoke(_data)
            }

            root.setOnClickListener {
                checkBox.nextChecked()
            }
        }
    }

    class Holder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)
}