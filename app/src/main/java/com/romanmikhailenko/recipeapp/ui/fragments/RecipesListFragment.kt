package com.romanmikhailenko.recipeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.romanmikhailenko.recipeapp.databinding.FragmentRecipesListBinding
import com.romanmikhailenko.recipeapp.ui.adapters.RecipesListAdapter
import com.romanmikhailenko.recipeapp.ui.viewmodels.RecipesListState
import com.romanmikhailenko.recipeapp.ui.viewmodels.RecipesListViewModel
import java.lang.IllegalStateException

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val recipesListViewModel: RecipesListViewModel by viewModels()
    private val recipesListFragmentArgs : RecipesListFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipesListViewModel.loadRecipes(
            recipesListFragmentArgs.category.id,
            recipesListFragmentArgs.category.title,
            recipesListFragmentArgs.category.imageUrl,
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