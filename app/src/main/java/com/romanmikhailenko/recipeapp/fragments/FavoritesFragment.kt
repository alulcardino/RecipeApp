package com.romanmikhailenko.recipeapp.fragments

import STUB_RECIPES
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.romanmikhailenko.recipeapp.ARG_RECIPE
import com.romanmikhailenko.recipeapp.PREFERENCE_FAVORITES
import com.romanmikhailenko.recipeapp.PREFERENCE_FAVORITES_KEY
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.adapters.RecipesListAdapter
import com.romanmikhailenko.recipeapp.databinding.FragmentCategoriesListBinding
import com.romanmikhailenko.recipeapp.databinding.FragmentFavoritesBinding
import com.romanmikhailenko.recipeapp.model.Recipe

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val recipesListAdapter = RecipesListAdapter(getFavoritesById(), this)
        recipesListAdapter.setOnClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        mBinding.rvFavoriteRecipes.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val categoryName = STUB_RECIPES.burgerRecipes[recipeId].title
        val categoryImageUrl = STUB_RECIPES.burgerRecipes[recipeId].imageUrl

        val recipe = STUB_RECIPES.burgerRecipes[recipeId]
        val bundle = bundleOf(
            Pair(ARG_RECIPE, recipe)
        )

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref = activity?.getSharedPreferences(PREFERENCE_FAVORITES, Context.MODE_PRIVATE)
        val ids = HashSet(
            sharedPref?.getStringSet(PREFERENCE_FAVORITES_KEY, HashSet<String>()) ?: setOf()
        )
        return ids
    }

    private fun getFavoritesById() : List<Recipe>{
        val ids = getFavorites().map {
            it.toInt()
        }
        val favorites = mutableListOf<Recipe>()
        ids.forEach {
            favorites.add(STUB_RECIPES.burgerRecipes[it])
        }
        return favorites
    }
}