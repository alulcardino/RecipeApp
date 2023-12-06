package com.romanmikhailenko.recipeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.model.Ingredient

class IngredientsAdapter(
    private val dataset: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIngredientName: TextView
        var tvIngredientAmount: TextView

        init {
            tvIngredientName = itemView.findViewById(R.id.tvIngredientName)
            tvIngredientAmount = itemView.findViewById(R.id.tvIngredientAmount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        )
    }
    override fun getItemCount() : Int {
        println()
        return dataset.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val currentItem = dataset[position]
        with(holder) {
            tvIngredientName.text = currentItem.unitOfMeasure
            tvIngredientAmount.text = "${currentItem.description} ${currentItem.quantity}"
        }
    }
}