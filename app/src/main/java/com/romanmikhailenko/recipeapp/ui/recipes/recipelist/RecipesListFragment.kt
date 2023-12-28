package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import STUB
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_ID
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_IMAGE_URL
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_NAME
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipesListBinding
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE_ID
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeFragment
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeFragmentDirections
import java.lang.Exception
import java.lang.IllegalStateException

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val recipesListViewModel: RecipesListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryName = requireArguments().getString(ARG_CATEGORY_NAME) ?: ""
        val categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL) ?: ""
        recipesListViewModel.loadRecipes(
            requireArguments().getInt(ARG_CATEGORY_ID),
            categoryName,
            categoryImageUrl,
        )
        recipesListViewModel.recipesState.value?.let { unitUI(it) }
    }

    private fun unitUI(state: RecipesListState) {
        recipesListViewModel.recipesState.observe(viewLifecycleOwner) {
            mBinding.tvCategoryTitle.text = state.categoryName
            mBinding.ivCategoryTitle.setImageDrawable(state.categoryImageUrl)
            initRecycler(state)
        }
    }

    private fun initRecycler(state: RecipesListState) {
        val recipesListAdapter = RecipesListAdapter(state.recipes, this)
        recipesListAdapter.setOnClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        mBinding.rvRecipes.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val action = RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }
}