package com.romanmikhailenko.recipeapp.ui.viewmodels

import STUB
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanmikhailenko.recipeapp.model.Category

data class CategoriesListState(
    var categories: List<Category> = listOf()
)

class CategoriesListViewModel : ViewModel() {
    private val _recipe = MutableLiveData(CategoriesListState())
    val categoriesState: LiveData<CategoriesListState>
        get() = _recipe

    fun loadCategories() {
       _recipe.value?.categories = STUB.getCategories()
    }
}
