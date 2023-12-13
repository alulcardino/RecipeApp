package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R


class MethodAdapter(
    private val dataset: List<String>,
) : RecyclerView.Adapter<MethodAdapter.MethodViewHolder>() {

    class MethodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMethod: TextView

        init {
            tvMethod = itemView.findViewById(R.id.tvMethod)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        return MethodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_method, parent, false)
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        holder.tvMethod.text = "${position + 1}. ${dataset[position]}"
    }
}