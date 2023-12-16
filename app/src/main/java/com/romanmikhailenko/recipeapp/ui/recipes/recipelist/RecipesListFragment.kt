package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import STUB_RECIPES
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_ID
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_IMAGE_URL
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_NAME
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipesListBinding
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE_ID
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeFragment
import java.lang.Exception
import java.lang.IllegalStateException

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        mBinding.tvCategoryTitle.text = categoryName
        try {
            val drawable = Drawable.createFromStream(
                this.context?.assets?.open(categoryImageUrl ?: "burger.png"), null
            )
            mBinding.ivCategoryTitle.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
        }
    }

    private fun initRecycler() {
        val recipesListAdapter = RecipesListAdapter(STUB_RECIPES.burgerRecipes, this)
        recipesListAdapter.setOnClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        mBinding.rvRecipes.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val bundle = bundleOf(
            Pair(ARG_RECIPE_ID, recipeId)
        )

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
        }
    }


}