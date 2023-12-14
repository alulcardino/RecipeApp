package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.model.Recipe
import java.io.InputStream
import java.lang.Exception

class RecipesListAdapter(
    private val dataset: List<Recipe>,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null


    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class RecipesViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val cvRecipe: CardView
        val ivRecipeImage: ImageView
        val tvRecipeName: TextView

        init {
            cvRecipe = itemView.findViewById(R.id.cvRecipeItem)
            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage)
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentItem = dataset[position]
        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(currentItem.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.ivRecipeImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
        }
        with(holder) {
            tvRecipeName.text = currentItem.title
            cvRecipe.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }

}