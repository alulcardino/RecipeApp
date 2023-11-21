package com.romanmikhailenko.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.romanmikhailenko.recipeapp.databinding.FragmentCategoriesListBinding
import com.romanmikhailenko.recipeapp.model.Category


class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val mBinding get() = _binding!!
    private val listOfCategory: List<Category> = listOf(
        Category("Рецепты всех популярных видов бургеров", 0, "burger.png", "Бургеры"),
        Category("Самые вкусные рецепты десертов специально для вас", 1, "dessert.png", "Десерты"),
        Category("Пицца на любой вкус и цвет. Лучшая подборка для тебя", 2, "pizza.png", "Пицца"),
        Category("Печеная, жареная, сушеная, любая рыба на твой вкус", 3, "fish.png", "Рыба"),
        Category("От классики до экзотики: мир в одной тарелке", 4, "soup.png", "Супы"),
        Category("Хрустящий калейдоскоп под соусом вдохновения", 5, "salad.png", "Салаты"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        openRecipesByCategoryId(categoriesListAdapter)
        mBinding.rvCategories.apply {
            adapter = categoriesListAdapter
        }
    }

    private fun openRecipesByCategoryId(categoriesListAdapter: CategoriesListAdapter) {
        categoriesListAdapter.setOnClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick() {
                parentFragmentManager.commit {
                    replace<RecipesListFragment>(R.id.mainContainer)
                    setReorderingAllowed(true)
                }
            }
        })
    }

}