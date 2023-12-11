package com.romanmikhailenko.recipeapp.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.ARG_RECIPE
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.RecyclerViewItemDecoration
import com.romanmikhailenko.recipeapp.adapters.IngredientsAdapter
import com.romanmikhailenko.recipeapp.adapters.MethodAdapter
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipeBinding
import com.romanmikhailenko.recipeapp.model.Recipe
import java.lang.Exception


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val mBinding get() = _binding!!
    private var recipe: Recipe? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }
        initUI()
        initRecyclers()
        initClickFavoriteButton()
    }

    private fun initUI() {
        mBinding.tvRecipeTitle.text = recipe?.title
        try {
            val drawable = Drawable.createFromStream(
                this.context?.assets?.open(recipe?.imageUrl ?: "burger.png"), null
            )
            mBinding.ivRecipe.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
        }
    }

    private fun initRecyclers() {

        val methodAdapter = MethodAdapter(recipe?.method ?: listOf())
        val ingredientsAdapter = IngredientsAdapter(recipe?.ingredients ?: listOf())
        val divider = RecyclerViewItemDecoration(this.context, R.drawable.divider)
        with(mBinding.rvMethod) {
            addItemDecoration(divider)
            adapter = methodAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
        with(mBinding.rvIngredients) {
            addItemDecoration(divider)
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }

        mBinding.sbPortions.setOnSeekBarChangeListener (object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                ingredientsAdapter.updateIngredients(p1)
                mBinding.tvPortionsCount.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun initClickFavoriteButton() {
        val emptyIcon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_heart)
        val fulledIcon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_heart_empty)
        with(mBinding.btnRecipeFavorite) {
            setOnClickListener {
                if (drawable == emptyIcon) {
                    setImageDrawable(fulledIcon)
                } else {
                    setImageDrawable(emptyIcon)
                }
            }
        }

    }

}