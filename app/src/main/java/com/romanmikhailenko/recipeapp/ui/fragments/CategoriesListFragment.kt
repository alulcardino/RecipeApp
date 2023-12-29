package com.romanmikhailenko.recipeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.romanmikhailenko.recipeapp.databinding.FragmentCategoriesListBinding
import com.romanmikhailenko.recipeapp.model.Category
import com.romanmikhailenko.recipeapp.ui.adapters.CategoriesListAdapter
import com.romanmikhailenko.recipeapp.ui.viewmodels.CategoriesListState
import com.romanmikhailenko.recipeapp.ui.viewmodels.CategoriesListViewModel
import java.lang.IllegalStateException


class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")

    private val categoriesListViewModel: CategoriesListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesListViewModel.loadCategories()
        categoriesListViewModel.categoriesState.value?.let { unitUi(it) }
    }

    private fun unitUi(categoriesListState: CategoriesListState) {
        categoriesListViewModel.categoriesState.observe(viewLifecycleOwner) {
            val categoriesListAdapter = CategoriesListAdapter(categoriesListState.categories, this)
            categoriesListAdapter.setOnClickListener(object :
                CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId, categoriesListState)
                }
            })
            mBinding.rvCategories.adapter = categoriesListAdapter
        }
    }

    private fun openRecipesByCategoryId(categoryId: Int, categoriesListState: CategoriesListState) {
        val categoryName = categoriesListState.categories[categoryId].title
        val categoryImageUrl = categoriesListState.categories[categoryId].imageUrl

        val action = CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
            Category(
                categoryId,
                categoryName,
                "",
                categoryImageUrl
            )
        )
        findNavController().navigate(action)
    }
}