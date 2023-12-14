package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.romanmikhailenko.recipeapp.model.Ingredient

data class RecipeState(
    var id: Int = 0,
    var imageUrl: String = "",
    var ingredients: List<Ingredient> = listOf(),
    var method: List<String> = listOf(),
    var title: String = "Recipe"
)

class RecipeViewModel : ViewModel() {
}