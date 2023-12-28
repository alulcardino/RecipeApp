package com.romanmikhailenko.recipeapp.ui.recipes.recipelist

import STUB
import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanmikhailenko.recipeapp.model.Category
import com.romanmikhailenko.recipeapp.model.Recipe
import com.romanmikhailenko.recipeapp.ui.recipes.recipe.RecipeState
import java.lang.Exception

data class RecipesListState(
    var recipes: List<Recipe> = listOf(),
    var categoryName: String = "",
    var categoryImageUrl: Drawable? = null,
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {
    private var _recipesState = MutableLiveData(RecipesListState())
    val recipesState: LiveData<RecipesListState>
        get() = _recipesState

    fun loadRecipes(id: Int, categoryName: String, categoryImageUrl: String ) {
        val recipes = STUB.getRecipesByCategoryId(id)
        _recipesState.value = RecipesListState(
            recipes = recipes,
            categoryName = categoryName,
            categoryImageUrl = getDrawable(categoryImageUrl)
        )
    }

    private fun getDrawable(categoryImageUrl: String?): Drawable? {
        return try {
            Drawable.createFromStream(
                application.assets?.open(categoryImageUrl ?: "burger.png"), null
            )
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
            null
        }
    }



}