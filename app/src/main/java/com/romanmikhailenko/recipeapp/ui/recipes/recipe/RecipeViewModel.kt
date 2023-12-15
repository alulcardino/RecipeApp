package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanmikhailenko.recipeapp.model.Ingredient
import com.romanmikhailenko.recipeapp.model.Recipe

data class RecipeState(
    var recipe: Recipe? = null,
    var portions: Int = 1,
    var isFavorite: Boolean = false
)

class RecipeViewModel : ViewModel() {
    private val _recipe = MutableLiveData(RecipeState())
    val recipe: MutableLiveData<RecipeState>
        get() = _recipe
}