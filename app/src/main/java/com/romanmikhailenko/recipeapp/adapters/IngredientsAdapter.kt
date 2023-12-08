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

    private var quantity: Int = 1

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

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val currentItem = dataset[position]
        with(holder) {
            tvIngredientName.text = currentItem.description
            tvIngredientAmount.text =
                "${getQuantityString(currentItem.quantity)} ${currentItem.unitOfMeasure}"
        }
    }

    private fun getQuantityString(input: String): String {
        val number = input.toDoubleOrNull()
        if (number != null) {
            return if (number % 1.0 != 0.0) {
                String.format("%.1f", number * quantity)
            } else {
                (number * quantity).toInt().toString()
            }
        }
        return ""
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }
}