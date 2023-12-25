package com.romanmikhailenko.recipeapp.ui.category

import STUB
import android.os.Bundle
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
import com.romanmikhailenko.recipeapp.databinding.FragmentCategoriesListBinding
import com.romanmikhailenko.recipeapp.model.Category
import com.romanmikhailenko.recipeapp.ui.recipes.recipelist.RecipesListFragment
import java.lang.IllegalStateException


class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val mBinding
        get() = _binding ?: throw IllegalStateException("Can't load view")
    private val listOfCategory: List<Category> = STUB.getCategories()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categoriesListAdapter = CategoriesListAdapter(listOfCategory, this)
        categoriesListAdapter.setOnClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        mBinding.rvCategories.adapter = categoriesListAdapter
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val categoryName = listOfCategory[categoryId].title
        val categoryImageUrl = listOfCategory[categoryId].imageUrl
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl
        )


        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}