package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import STUB
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES_KEY
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentFavoritesBinding
import com.romanmikhailenko.recipeapp.model.Recipe
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE
import com.romanmikhailenko.recipeapp.ui.ARG_RECIPE_ID
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeFragment
import java.lang.IllegalStateException

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.loadFavorites()
        favoriteViewModel.favoriteState.value?.let { initUI(it) }
    }

    private fun initUI(state: FavoriteState) {
        favoriteViewModel.favoriteState.observe(viewLifecycleOwner) {
            initRecycler(state)
        }
    }

    private fun initRecycler(state: FavoriteState) {
        val recipesListAdapter = RecipesListAdapter(state.recipes, this)
        recipesListAdapter.setOnClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        mBinding.rvFavoriteRecipes.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }

}