package com.romanmikhailenko.recipeapp.ui.viewmodels

import STUB
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romanmikhailenko.recipeapp.model.Recipe
import com.romanmikhailenko.recipeapp.utils.PREFERENCE_FAVORITES
import com.romanmikhailenko.recipeapp.utils.PREFERENCE_FAVORITES_KEY
import java.lang.Exception

data class RecipeState(
    var recipe: Recipe? = null,
    var portions: Int = 1,
    var isFavorite: Boolean = false,
    var recipeDrawable: Drawable? = null,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _recipe = MutableLiveData(RecipeState())
    val recipeState: LiveData<RecipeState>
        get() = _recipe

    init {
        Log.i("!!!", "Init block")
        _recipe.value = recipeState.value?.copy(isFavorite = false)
    }

    fun loadRecipe(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        _recipe.value = RecipeState(
            recipe = recipe,
            portions = _recipe.value?.portions ?: 1,
            isFavorite = getFavorites().contains(recipe.id.toString()),
            recipeDrawable = getDrawable(recipe)
        )
        //TODO("load from network")
    }

    private fun getDrawable(recipe: Recipe?): Drawable? {
        return try {
            Drawable.createFromStream(
                application.assets?.open(recipe?.imageUrl ?: "burger.png"), null
            )
        } catch (e: Exception) {
            Log.e("MyLog", e.stackTraceToString())
            null
        }
    }

    fun onFavoritesClicked() {
        val favorites = getFavorites()
        val recipeId = recipeState.value?.recipe?.id.toString()
        if (favorites.contains(recipeId)) {
            favorites.remove(recipeId)
            _recipe.value = recipeState.value?.copy(isFavorite = false)
        } else {
            favorites.add(recipeId)
            _recipe.value = recipeState.value?.copy(isFavorite = true)

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
            this?.putStringSet(PREFERENCE_FAVORITES_KEY, ids)
            this?.apply()
        }
    }

    fun onChangePortions(p1: Int) {
        _recipe.value = recipeState.value?.copy(portions = p1)
    }
}