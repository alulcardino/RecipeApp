package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipeBinding
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE_ID
import java.lang.Exception
import java.lang.IllegalStateException


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel.loadRecipe(requireArguments().getInt(ARG_RECIPE_ID))
        initObserver()
        initRecyclers(recipeViewModel.recipeState.value)
        initClickFavoriteButton(recipeViewModel.recipeState.value)
    }

    private fun initObserver() {
        recipeViewModel.recipeState.observe(viewLifecycleOwner) {
            initUI(recipeViewModel.recipeState.value)
            Log.i("!!!", recipeViewModel.recipeState.value?.isFavorite.toString())
        }
    }

    private fun initUI(recipeState: RecipeState?) {
        mBinding.tvRecipeTitle.text = recipeState?.recipe?.title
        try {
            val drawable = Drawable.createFromStream(
                this.context?.assets?.open(recipeState?.recipe?.imageUrl ?: "burger.png"), null
            )
            mBinding.ivRecipe.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
        }
    }

    private fun initRecyclers(recipeState: RecipeState?) {
        val methodAdapter = MethodAdapter(recipeState?.recipe?.method ?: listOf())
        val ingredientsAdapter = IngredientsAdapter(recipeState?.recipe?.ingredients ?: listOf())
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

        mBinding.sbPortions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    private fun initClickFavoriteButton(recipeState: RecipeState?) {
        val emptyIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart_empty) }
        val fulledIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_heart) }

        if (recipeState?.isFavorite == true) {
            mBinding.ibtnRecipeFavorite.setImageDrawable(fulledIcon)
        } else {
            mBinding.ibtnRecipeFavorite.setImageDrawable(emptyIcon)
        }
        with(mBinding.ibtnRecipeFavorite) {
            setOnClickListener {
                recipeViewModel.onFavoritesClicked()
                if (recipeState?.isFavorite == true) {
                    mBinding.ibtnRecipeFavorite.setImageDrawable(fulledIcon)
                } else {
                    mBinding.ibtnRecipeFavorite.setImageDrawable(emptyIcon)
                }
            }
        }
    }


}