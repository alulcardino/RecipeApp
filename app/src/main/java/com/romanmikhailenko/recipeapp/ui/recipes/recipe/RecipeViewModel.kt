package com.romanmikhailenko.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.romanmikhailenko.recipeapp.R
import com.romanmikhailenko.recipeapp.model.Recipe
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES
import com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES_KEY

data class RecipeState(
    var recipe: Recipe? = null,
    var portions: Int = 1,
    var isFavorite: Boolean = false
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _recipe = MutableLiveData(RecipeState())
    val recipeState: MutableLiveData<RecipeState>
        get() = _recipe

    init {
        Log.i("!!!", "Init block")
        recipeState.value = recipeState.value?.copy(isFavorite = false)
    }

    fun loadRecipe(recipeId: Int) {
        with(recipeState) {
            value?.recipe = STUB_RECIPES.burgerRecipes[recipeId]
            value?.isFavorite = getFavorites().contains(value?.recipe?.id.toString())
            value?.portions = value?.portions ?: 1
        }
        // TODO(load from network)
    }

    fun onFavoritesClicked() {
        val favorites = getFavorites()
        if (favorites.contains(recipeState.value?.recipe?.id.toString())) {
            favorites.remove(recipeState.value?.recipe?.id.toString())
            recipeState.value = recipeState.value?.copy(isFavorite = false)
        } else {
            favorites.add(recipeState.value?.recipe?.id.toString())
            recipeState.value = recipeState.value?.copy(isFavorite = true)

        }
        saveFavorites(favorites)
    }

private fun getFavorites(): MutableSet<String> {
    val sharedPref =
        application.getSharedPreferences(PREFERENCE_FAVORITES, Context.MODE_PRIVATE)
    return HashSet(
        sharedPref?.getStringSet(PREFERENCE_FAVORITES_KEY, HashSet()) ?: setOf()
    )
}

private fun saveFavorites(ids: Set<String>) {
    val sharedPref =
        application.getSharedPreferences(PREFERENCE_FAVORITES, Context.MODE_PRIVATE)
    with(sharedPref?.edit()) {
        this?.putStringSet(com.romanmikhailenko.recipeapp.ui.PREFERENCE_FAVORITES_KEY, ids)
        this?.apply()
    }
}
}