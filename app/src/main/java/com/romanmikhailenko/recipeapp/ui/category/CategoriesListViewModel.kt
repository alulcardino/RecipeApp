package com.romanmikhailenko.recipeapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeState

class CategoriesListViewModel : ViewModel() {
    private val _recipe = MutableLiveData(RecipeState())
    val categoriesState: LiveData<RecipeState>
}