package com.romanmikhailenko.recipeapp.ui.category

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
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_ID
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_IMAGE_URL
import com.romanmikhailenko.recipeapp.ui.ARG_CATEGORY_NAME
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.databinding.FragmentCategoriesListBinding
import com.romanmikhailenko.recipeapp.model.Category
import com.romanmikhailenko.recipeapp.ui.recipes.recipelist.RecipesListFragment
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
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl
        )

        findNavController().navigate(R.id.recipesListFragment, args = bundle)
    }
}