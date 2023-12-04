package com.romanmikhailenko.recipeapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.romanmikhailenko.recipeapp.model.Category
import java.io.InputStream
import android.util.Log
import androidx.cardview.widget.CardView
import java.lang.Error
import java.lang.Exception


class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment
) : RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class CategoriesViewHolder(
        itemView: View

    ) : ViewHolder(itemView) {

        val ivCategoryImage: ImageView
        val tvCategoryTitle: TextView
        val tvCategoryDescription: TextView
        val cvCategory: CardView

        init {
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryItem)
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryName)
            tvCategoryDescription = itemView.findViewById(R.id.tvCategoryDescription)
            cvCategory = itemView.findViewById(R.id.cvCategoryItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = dataSet[position]
        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(currentItem.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.ivCategoryImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
        }

        with(holder) {
            tvCategoryTitle.text = currentItem.title
            tvCategoryDescription.text = currentItem.description
            cvCategory.setOnClickListener {
                itemClickListener?.onItemClick(dataSet.indexOf(currentItem))
            }
        }
    }

}